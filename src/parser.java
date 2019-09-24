package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.nodes.*;
import src.parseTree.tokens.*;

import java.util.*;

import static src.parseSet.*;

public class parser {

    private static boolean first(Object node, Object token) {
        String nodeName = node.getClass().getSimpleName();
        if (node instanceof String) nodeName = (String) node;
        String tokenName = "EOF";
        if (token != null) tokenName = token.getClass().getSimpleName();
        return FIRST.get(nodeName).get(tokenName);
    }

    private static List<String> predict(Object node, Object token) {
        String nodeName = node.getClass().getSimpleName();
        if (node instanceof String) nodeName = (String) node;
        String tokenName = "EOF";
        if (token != null) tokenName = token.getClass().getSimpleName();
        System.out.println(nodeName + " " + tokenName);
        if (PREDICT.get(nodeName) == null || PREDICT.get(nodeName).get(tokenName) == null) return new ArrayList<>();
        return PREDICT.get(nodeName).get(tokenName);
    }

    /**
     * Parse the tokenList
     * todo lookahead for signed int/dbl and int/dbl exprs
     * todo tokenize EOF
     * @param tokenList list of tokens
     * @return  the program
     */
    public static program parse(List<token> tokenList) {
        loadParseSets();
        int readCursor = 0; // position in tokenList
        Deque<Object> stack = new ArrayDeque<>(); // contains all terminals and non-terminals
        Deque<node> visited = new ArrayDeque<>(); // contains only non-terminals
        tokenList.add(null);
        program root = new program();
        stack.push(root);
        while (!stack.isEmpty()) {
            Object leaf = stack.peek();
            node node = visited.peek();
            if (leaf == node) { // finished adding all children of node into node
                visited.pop();
                node = visited.peek();
                if (node != null) node.addChild(stack.pop()); // node == null if program was removed
                continue;
            }
            // read token
            token token = null;
            if (readCursor < tokenList.size()) token = tokenList.get(readCursor);
            else errorPrinter.throwError(-1, new Syntax("F"));
            // if leaf cannot start with token, print error
            if (!first(leaf, token)) errorPrinter.throwError((token == null) ? -1: token.getLineNumber(),
                    new Syntax(String.format("Parse error %s %s",
                            leaf.getClass().getSimpleName(), (token == null) ? "null": token.toString())
                    ));
            // get children of leaf
            List<String> children = predict(leaf, token);
            if (leaf instanceof String) stack.pop(); // handle abstract classes
            // add children to stack
            for (int i = children.size() - 1; i >= 0; i--) { // add children to stack in reverse order
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
            // if no children, it was a token
            // remove from stack, add to node in visited
            if (children.size() <= 0) {
                if (node != null) node.addChild(token);
                readCursor++;
            }
            else if (!(leaf instanceof String)) visited.push((node) leaf); // handle abstract node
        }
        return root;
    }
}
