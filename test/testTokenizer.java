package test;

import src.parseTree.nodes.*;
import src.parseTree.tokens.token;
import src.tokenizer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static src.nameTableSingleton.init_nameTable;
import static src.parser.parse;

public class testTokenizer {

    /**
     * Provides a visual representation of parse tree.
     *
     * @param gen      number of tabs to align children of same parent
     * @param children children to ouput
     */
    private static void parseOutput(int gen, List<Object> children) {
        if (children.size() <= 0) return;
        for (Object child : children) {
            for (int i = 0; i < gen; i++) System.out.print("\t");
            System.out.print(child.getClass().getSimpleName());
            if (child instanceof token) System.out.print("(" + child.toString() + ")");
            System.out.println();
            List<Object> newChildren = new ArrayList<>();
            switch (child.getClass().getSimpleName()) {
                case "asmt":
                    newChildren = ((asmt) child).getChildren();
                    break;
                case "charAt_expr":
                    newChildren = ((charAt_expr) child).getChildren();
                    break;
                case "concat_expr":
                    newChildren = ((concat_expr) child).getChildren();
                    break;
                case "double_expr":
                    newChildren = ((double_expr) child).getChildren();
                    break;
                case "int_expr":
                    newChildren = ((int_expr) child).getChildren();
                    break;
                case "print_stmt":
                    newChildren = ((print_stmt) child).getChildren();
                    break;
                case "program":
                    newChildren = ((program) child).getChildren();
                    break;
                case "stmt_lst":
                    newChildren = ((stmt_lst) child).getChildren();
                    break;
                case "str_literal":
                    newChildren = ((str_literal) child).getChildren();
                    break;
                default: // token
                    break;
            }
            parseOutput(gen + 1, newChildren);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Error: Specify a file");
            System.exit(1);
        } else {
            testFile(args[0]);
        }
<<<<<<< HEAD
    }

    private static void testFile(String filepath) {
        init_nameTable(filepath);
=======
        else {
            init_nameTable(args[0]);
            // tokenizer
            List<token> tokenList = tokenizer.tokenize(args[0]);
>>>>>>> master

        List<token> tokenList;
        try {
            tokenList = tokenizer.tokenize(filepath);
        } catch (FileNotFoundException f) {
            System.err.println("File not found: " + filepath);
            return;
        }

        System.out.println("\n\n\tToken List");
        int currentLine = 0;
        for (token tok : tokenList) {
            if (tok.getLineNumber() != currentLine) {
                currentLine = tok.getLineNumber();
                String numLead = String.format("%2d", currentLine);
                System.out.print("\nLine " + numLead + ":\t");
            }

<<<<<<< HEAD
            System.out.print(tok + " | ");
=======
            // parser
            List<Object> children = new ArrayList<>();
            children.add(parse(tokenList));
            int gen = 0;
            parseOutput(gen, children);
>>>>>>> master
        }
        System.out.println("\n\n");

        List<Object> children = new ArrayList<>();
        children.add(parse(tokenList));
        int gen = 0;
        parseOutput(gen, children);
    }
}
