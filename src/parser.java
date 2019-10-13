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
                    //todo Check for negation of IDs and for multiple signs before any given number
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
                typeIdx type = symTab.get(token.toString());
                if (child instanceof String && child.equals("id")) { // only time id is specifically required is for asmt
                    if (type != null) errorPrinter.throwError(token.getLineNumber(), new Syntax("Reassignment not allowed"));
                } else {
                    if (type == null) { // referencing inexistent name
                        // check if referencing the id is even valid
                        if (!first(child, new double_token(0, 0)) &&
                                (!first(child, new int_token(0, 0))) &&
                                (!first(child, new str_token(0, "")))) {
                            String childName = child.getClass().getSimpleName();
                            if (child instanceof String) childName = (String) child;
                            String tokenName = token.getClass().getSimpleName();
                            errorPrinter.throwError(token.getLineNumber(),
                                    new Syntax(String.format("%s expected but got %s", childName, tokenName)));
                        }
                        errorPrinter.throwError(token.getLineNumber(), new Syntax("Unknown identifier"));
                    } else {
                        // treat id as its reference
                        switch (type) {
                            case k_Double:
                                dummy = new double_token(token.getLineNumber(), 0.0);
                                break;
                            case k_Integer:
                                dummy = new int_token(token.getLineNumber(), 0);
                                break;
                            case k_String:
                                dummy = new str_token(token.getLineNumber(), "");
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
                int lineNumber = token.getLineNumber();
                if (token instanceof EOF) lineNumber--;
                errorPrinter.throwError(lineNumber,
                        new Syntax(String.format("%s expected but got %s", childName, tokenName)));
            }

            List<String> children = predict(child, dummy);

            // default: d_expr/i_expr -> d_token/i_token
            // lookahead(1) to check for op
            // if op, d_expr/i_expr -> d_token/i_token,op,d_expr,i_expr
            if (child instanceof double_expr || child instanceof int_expr) {
                token nextToken = tokenList.get(t_idx + 1);
                if (nextToken instanceof op) {
                    if (child instanceof double_expr) stack.push(new double_expr());
                    else stack.push(new int_expr());
                    stack.push("op");
                }
            }

            if (child instanceof String) stack.pop(); // remove tokens and abstract nodes
            if (children.size() <= 0) { // if no children, it is a token
                if (parent != null) parent.addChild(token); // add to parent
                t_idx++;
            } else if (child instanceof node) parents.push((node) child);

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
