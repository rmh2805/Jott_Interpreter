package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.nodes.*;
import src.parseTree.tokens.*;

import java.util.*;

import static src.nameTableSingleton.getInstance;
import static src.parseSet.*;

public class parser {

    private static boolean first(Object node, Object token) {
        String nodeName = node.getClass().getSimpleName();
        if (node instanceof String) nodeName = (String) node;
        String tokenName = token.getClass().getSimpleName();
        if (FIRST.get(nodeName) == null || FIRST.get(nodeName).get(tokenName) == null) return false;
        return FIRST.get(nodeName).get(tokenName);
    }

    private static List<String> predict(Object node, Object token) {
        String nodeName = node.getClass().getSimpleName();
        if (node instanceof String) nodeName = (String) node;
        String tokenName = token.getClass().getSimpleName();
        if (PREDICT.get(nodeName) == null || PREDICT.get(nodeName).get(tokenName) == null) return new ArrayList<>();
        return PREDICT.get(nodeName).get(tokenName);
    }

    /**
     * Parse the tokenList
     * todo process asmt
     * @param tokenList list of tokens
     * @return  the program
     */
    public static program parse(List<token> tokenList) {
        loadParseSets();
        int readCursor = 0; // position in tokenList
        Deque<Object> stack = new ArrayDeque<>(); // contains all terminals and non-terminals
        Deque<node> visited = new ArrayDeque<>(); // contains only non-terminals
        program root = new program();
        stack.push(root);
        while (!stack.isEmpty()) {
            Object leaf = stack.peek();
            node node = visited.peek();
            if (leaf == node) { // finished adding all children of node into node
                visited.pop();
                leaf = stack.pop();
                node = visited.peek();
                if (node != null) node.addChild(leaf); // node == null if derivations merged into start symbol
                continue;
            }
            // read token
            token token = null;
            if (readCursor < tokenList.size()) token = tokenList.get(readCursor);
            else errorPrinter.throwError(-1, new Syntax("F"));
            switch (token.toString()) {
                case "+":
                case "-":
                    token nextToken = tokenList.get(readCursor + 1);
                    if (first(leaf, nextToken)) {
                        if ("-".equals(token.toString())) {
                            if (nextToken instanceof int_token) ((int_token) nextToken).negate();
                            else if (nextToken instanceof double_token) ((double_token) nextToken).negate();
                        }
                        token = nextToken;
                        readCursor++;
                    }
            }
            // if leaf cannot start with token, print error
            if (!first(leaf, token)) errorPrinter.throwError((token == null) ? -1: token.getLineNumber(),
                    new Syntax(String.format("Parse error %s %s",
                            leaf.getClass().getSimpleName(), (token == null) ? "null": token.getClass().getSimpleName())
                    ));
            List<String> children;
            if (!(node instanceof asmt) && token instanceof id) {
                typeIdx type = getInstance().getType((id) token);
                String tokenName = "id";
                if (type != null)
                    switch (type) {
                        case k_Double:
                            tokenName = "double_token";
                            break;
                        case k_Integer:
                            tokenName = "int_token";
                            break;
                        case k_String:
                            tokenName = "str_token";
                            break;
                    }
                else errorPrinter.throwError(token.getLineNumber(), new Syntax("Unknown identifier"));
                children = predict(leaf, tokenName);
            }
            else children = predict(leaf, token);
            if (leaf instanceof double_expr || leaf instanceof int_expr) {
                token nextToken = tokenList.get(readCursor + 1);
                if (nextToken instanceof op) {
                    if (leaf instanceof double_expr) stack.push(new double_expr());
                    else stack.push(new int_expr());
                    stack.push("op");
                }
            }
            if (leaf instanceof String) stack.pop(); // handle abstract classes and tokens
            for (int i = children.size() - 1; i >= 0; i--) { // push on stack in reverse order
                String child = children.get(i);
                Object newLeaf;
                switch (child) {
                    case "asmt":
                        newLeaf = new asmt();
                        break;
                    case "charAt_expr":
                        newLeaf = new charAt_expr();
                        break;
                    case "concat_expr":
                        newLeaf = new concat_expr();
                        break;
                    case "double_expr":
                        newLeaf = new double_expr();
                        break;
                    case "int_expr":
                        newLeaf = new int_expr();
                        break;
                    case "print_stmt":
                        newLeaf = new print_stmt();
                        break;
                    case "stmt_lst":
                        newLeaf = new stmt_lst();
                        break;
                    case "str_literal":
                        newLeaf = new str_literal();
                        break;
                    default: // token or abstract node
                        newLeaf = child;
                        break;
                }
                stack.push(newLeaf);
            }
            if (children.size() <= 0) { // if no children, it was a token
                if (node != null) node.addChild(token); // add to node in visited, token already removed from stack
                readCursor++;
            }
            else if (leaf instanceof node) visited.push((node) leaf);
        }
        return root;
    }
}
