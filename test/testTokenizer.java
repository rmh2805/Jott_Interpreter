package test;

import src.parseTree.nodes.node;
import src.parseTree.nodes.program;
import src.parseTree.nodes.stmt_lst;
import src.parseTree.tokens.token;
import src.tokenizer;

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
    private static void parseOutput(int gen, List<Object> children) throws ClassCastException {
        if (children.size() <= 0) return;

        for (Object child : children) {

            for (int i = 0; i < gen; i++)
                System.out.print("\t");

            if (child == null) System.out.print("\"\"");
            else System.out.print(child.getClass().getSimpleName());
            if (child instanceof token) {
                System.out.println("('" + child.toString() + "')");
                continue;
            }
            System.out.println();

            if(child instanceof stmt_lst)
                gen--;

            if(child instanceof node) {
                List<Object> newChildren = ((node) child).getChildren();
                parseOutput(gen + 1, newChildren);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Error: Specify a file");
            System.exit(1);
        }
        else {
            init_nameTable(args[0]);

            // tokenizer
            List<token> tokenList = tokenizer.tokenize(args[0]);

            System.out.println("\n\n\tToken List");
            int currentLine = 0;
            for (token tok : tokenList) {
                if (tok.getLineNumber() != currentLine) {
                    currentLine = tok.getLineNumber();
                    String numLead = String.format("%2d", currentLine);
                    System.out.print("\nLine " + numLead + ":\t");
                }

                System.out.print(tok + " | ");
            }
            System.out.println("\n\n");

            // parser
            List<Object> children = new ArrayList<>();
            program root = parse(tokenList);
            children.add(root);
            int gen = 0;
            parseOutput(gen, children);

            root.execute();
        }
    }

}
