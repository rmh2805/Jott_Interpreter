package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.nodes.*;
import src.parseTree.tokens.*;

import java.util.*;

import static src.parseSet.*;

public class parser {

    /**
     * Determines if node or leaf may begin with given token.
     *
     * @param parent    the node (node) or leaf (String)
     * @param tok       the token read
     * @return  true if 1) node may begin with token
     *          or 2) leaf (token required) is token received
     *          false otherwise
     */
    private static boolean first(Object parent, token tok) {
        String parentName;
        if (parent instanceof String)
            parentName = (String) parent;
        else
            parentName = parent.getClass().getSimpleName();

        String tokenName = tok.getClass().getSimpleName();

        if (FIRST.get(parentName) == null || FIRST.get(parentName).get(tokenName) == null)
            return false;
        else
            return FIRST.get(parentName).get(tokenName);
    }

    /**
     * Determines derivations of parent given token.
     *
     * @param parent    the node (node), abstract node (String), or leaf (String)
     * @param token     the token read
     * @return  list of derivations of parent as Strings (may be empty)
     *          or empty list (default)
     */
    private static List<String> predict(Object parent, token token) {
        String parentName;
        if (parent instanceof String)
            parentName = (String) parent;
        else
            parentName = parent.getClass().getSimpleName();

        String tokenName = token.getClass().getSimpleName();

        if (PREDICT.get(parentName) == null || PREDICT.get(parentName).get(tokenName) == null)
            return new ArrayList<>(); // assume to be valid, parent-token verified by first
        else
            return PREDICT.get(parentName).get(tokenName);
    }

    /**
     * Parse the tokenList
     *
     * @param tokenList list of tokens
     * @return the program
     */
    public static program parse(List<token> tokenList) {
        loadParseSets();
        int t_idx = 0; // position in tokenList
        Deque<Object> stack = new ArrayDeque<>(); // CFG derivations
        Deque<node> parents = new ArrayDeque<>();
        program root = new program();
        stack.push(root);
        Map<String, typeIdx> symTab = new HashMap<>();
        while (!stack.isEmpty()) {
            Object child = stack.peek();
            node parent = parents.peek();
            if (child == parent) { // processed all children of parent
                parents.pop();
                stack.pop();
                parent = parents.peek();
                if (parent != null) parent.addChild(child); // parent == null if derivations merged into start symbol
                if (child instanceof asmt) { // add to symbol table
                    String type = ((asmt) child).getType();
                    String name = ((asmt) child).getId();
                    switch (type) {
                        case "Double":
                            symTab.put(name, typeIdx.k_Double);
                            break;
                        case "Integer":
                            symTab.put(name, typeIdx.k_Integer);
                            break;
                        case "String":
                            symTab.put(name, typeIdx.k_String);
                            break;
                    }
                }
                continue;
            }

            token token = tokenList.get(t_idx);
            // handle eof
            if (token instanceof EOF && child instanceof stmt_lst) {
                stack.pop();
                continue;
            }

            if (!(child instanceof String && child.equals("op"))) { // child not "op"
                // handle signed double and integer
                switch (token.toString()) {
                    case "+":
                    case "-":
                        token nextToken = tokenList.get(t_idx + 1);
                        if (first(child, nextToken)) {
                            if ("-".equals(token.toString())) {
                                if (nextToken instanceof int_token) ((int_token) nextToken).negate();
                                else if (nextToken instanceof double_token) ((double_token) nextToken).negate();
                            }
                            token = nextToken;
                            t_idx++;
                        }
                }
            }

            token dummy = token;
            if (token instanceof id) {
                if (!first(child, token)) { // check if referencing the id is even valid
                    String childName = child.getClass().getSimpleName();
                    if (child instanceof String) childName = (String) child;
                    String tokenName = token.getClass().getSimpleName();
                    errorPrinter.throwError(token,
                            new Syntax(String.format("%s expected but got %s", childName, tokenName)));
                }

                typeIdx type = symTab.get(token.toString());
                if (child instanceof String && child.equals("id")) { // only time id is specifically required is for asmt
                    if (type != null) errorPrinter.throwError(token, new Syntax("Reassignment not allowed"));
                } else {
                    if (type == null) { // referencing inexistent name
                        errorPrinter.throwError(token, new Syntax("Unknown identifier"));
                    } else {
                        // treat id as its reference
                        switch (type) {
                            case k_Double:
                                dummy = new double_token(token.getLineNumber(), token.getIndex(), 0.0);
                                break;
                            case k_Integer:
                                dummy = new int_token(token.getLineNumber(), token.getIndex(), 0);
                                break;
                            case k_String:
                                dummy = new str_token(token.getLineNumber(), token.getIndex(), "");
                                break;
                        }
                    }
                }
            }

            // if child cannot start with token, print error
            if (!first(child, dummy)) {
                String childName = child.getClass().getSimpleName();
                if (child instanceof String) childName = (String) child;
                String tokenName = dummy.getClass().getSimpleName();
                if (childName.equals("rel_op")) {
                    childName = "Integer";
                    Object lhs = parent.getChildren().get(0);
                    if (lhs instanceof double_expr) tokenName = "Double";
                    else if (lhs instanceof str_expr) tokenName = "String";
                    else tokenName = lhs.getClass().getSimpleName();
                }
                errorPrinter.throwError(token,
                        new Syntax(String.format("%s expected but got %s", childName, tokenName)));
            }

            List<String> children = predict(child, dummy);

            if (child instanceof String) stack.pop(); // remove tokens and abstract nodes
            if (children.size() <= 0) { // if no children, it is a token
                if (parent != null) parent.addChild(token); // add to parent
                t_idx++;
            } else if (child instanceof node) {
                parent = (node) child;
                parents.push(parent);
            }

            token nextToken = tokenList.get(t_idx);
            // 1) if op follows an int_expr -> int_token or double_expr -> double_token, the expr is the parent
            // modify stack and parents so the T_expr is a left child of a new T_expr
            // result stack: ..., T_expr, op, T_token, T_expr (new), ...
            // result parents: ..., T_expr, T_expr (new), ...
            // 2) if op follows relational int_expr -> T_expr,rel_op,T_expr, the expr is the "grandparent"
            // modify stack and parents so the int_expr (grandparent) is left child of a new int_expr
            // result stack: ..., T_expr, int_expr, op, int_token, int_expr (new), ...
            // result parents: ..., T_expr, int_expr, int_expr (new), ...
            if (nextToken instanceof op) {
                parents.pop();
                if (token instanceof op) { // if currentToken is op, and nextToken is op,
                    ; // assume nextToken is signed number, not op
                }
                else if (parent instanceof int_expr ||
                        parent instanceof double_expr ||
                        parents.peek() instanceof int_expr && !parents.peek().isEmpty()) {
                    node grandparent = parents.peek();
                    node newParent = new double_expr();
                    if (parent instanceof int_expr ||
                            grandparent instanceof int_expr && !grandparent.isEmpty())
                        newParent = new int_expr();

                    Deque<Object> tempStack = new ArrayDeque<>();
                    while (stack.peek() != parent) tempStack.push(stack.pop()); // get to parent
                    tempStack.push(stack.pop()); // remove parent, insert just below parent
                    if (grandparent instanceof int_expr && !grandparent.isEmpty()) { // if condition 2, insert just below grandparent
                        tempStack.push(stack.pop());
                        parents.pop();
                    }

                    stack.push(newParent); // insert new parent
                    if (grandparent instanceof int_expr && !grandparent.isEmpty())
                        stack.push("int_token"); // push expected right child of new parent
                    else if (parent instanceof int_expr) stack.push("int_token");
                    else if (parent instanceof double_expr) stack.push("double_token");
                    stack.push("op");
                    while (!tempStack.isEmpty()) stack.push(tempStack.pop());

                    parents.push(newParent); // update parents
                    if (grandparent instanceof int_expr && !grandparent.isEmpty()) parents.push(grandparent);
                }
                parents.push(parent);
            }

            // 1) rel_op may change current expr type (to int_expr) if expr is child of print, which takes any type
            // 2) rel_op may follow an int_expr (parent) -> int_token or a relational int_expr (grandparent)
            if (nextToken instanceof rel_op) {
                parents.pop();
                if (parents.peek() instanceof int_expr && parents.peek().isEmpty()) { // handle only chaining of rel_ops
                    ; // ignore initial LHS expr of relational statement. rel_op,token already on stack
                }
                else if (parents.peek() instanceof print_stmt ||
                        parents.peek() instanceof int_expr ||
                        parent instanceof int_expr) {
                    node grandparent = parents.peek();
                    node newParent = new int_expr();

                    Deque<Object> tempStack = new ArrayDeque<>();
                    while (stack.peek() != parent) tempStack.push(stack.pop());
                    tempStack.push(stack.pop());
                    if (grandparent instanceof int_expr) {
                        tempStack.push(stack.pop());
                        parents.pop();
                    }

                    stack.push(newParent);
                    if (parent instanceof int_expr || grandparent instanceof int_expr) stack.push("int_token");
                    else if (parent instanceof double_expr) stack.push("double_token");
                    else if (parent instanceof str_expr) stack.push("str_expr");
                    stack.push("rel_op");
                    while (!tempStack.isEmpty()) stack.push(tempStack.pop());

                    parents.push(newParent);
                    if (grandparent instanceof int_expr) parents.push(grandparent);
                }
                parents.push(parent);
            }

            // add children to stack in reverse order
            for (int i = children.size() - 1; i >= 0; i--) {
                String name = children.get(i);
                Object newChild;
                switch (name) {
                    case "asmt":
                        newChild = new asmt();
                        break;
                    case "charAt_expr":
                        newChild = new charAt_expr();
                        break;
                    case "concat_expr":
                        newChild = new concat_expr();
                        break;
                    case "double_expr":
                        newChild = new double_expr();
                        break;
                    case "int_expr":
                        newChild = new int_expr();
                        break;
                    case "print_stmt":
                        newChild = new print_stmt();
                        break;
                    case "stmt_lst":
                        newChild = new stmt_lst();
                        break;
                    case "str_literal":
                        newChild = new str_literal();
                        break;
                    default: // token or abstract parent
                        newChild = name;
                        break;
                }
                stack.push(newChild);
            }
        }
        return root;
    }
}
