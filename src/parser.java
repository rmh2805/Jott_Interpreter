package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.nodes.*;
import src.parseTree.tokens.*;

import java.util.*;

import static src.parseSet.*;

public class parser {

    private static boolean first(Object parent, token token) {
        boolean result;
        String parentName = parent.getClass().getSimpleName();
        if (parent instanceof String) parentName = (String) parent;
        String tokenName = token.getClass().getSimpleName();
        if (FIRST.get(parentName) == null || FIRST.get(parentName).get(tokenName) == null) result = false;
        else result = FIRST.get(parentName).get(tokenName);
        return result;
    }

    private static List<String> predict(Object parent, Object token) {
        List<String> result;
        String parentName = parent.getClass().getSimpleName();
        if (parent instanceof String) parentName = (String) parent;
        String tokenName = token.getClass().getSimpleName();
        if (PREDICT.get(parentName) == null || PREDICT.get(parentName).get(tokenName) == null) result = new ArrayList<>();
        else result = PREDICT.get(parentName).get(tokenName);
        return result;
    }

    /**
     * Parse the tokenList
     *
     * @param tokenList list of tokens
     * @return  the program
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
                if (child instanceof asmt) {
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
            // treat id as its reference
            token dummy = token;
            if (!(parent instanceof asmt) && token instanceof id) {
                typeIdx type = symTab.get(token.toString());
                if (type != null)
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
                else errorPrinter.throwError(token.getLineNumber(), new Syntax("Unknown identifier"));
            }
            // if child cannot start with token, print error
            if (!first(child, dummy)) {
                String childName = child.getClass().getSimpleName();
                String tokenName = dummy.getClass().getSimpleName();
                if (child instanceof String)
                    errorPrinter.throwError(token.getLineNumber(),
                            new Syntax(String.format("%s expected but found %s", child, tokenName)));
                else
                    errorPrinter.throwError(token.getLineNumber(),
                            new Syntax(String.format("%s may not start with %s", childName, tokenName)));
            }

            List<String> children = predict(child, dummy);
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
            }
            else if (child instanceof node) parents.push((node) child);
            for (int i = children.size() - 1; i >= 0; i--) { // push on stack in reverse order
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
