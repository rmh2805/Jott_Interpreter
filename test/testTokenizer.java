package test;

import src.parseTree.nodes.program;
import src.parseTree.tokens.token;
import src.tokenizer;

import java.util.List;

import static src.parser.parse;

public class testTokenizer {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Error: Specify a file to tokenize");
            System.exit(1);
        }
        else {
            List<token> tokenList = tokenizer.tokenize(args[0]);

            System.out.println("\n\n\tToken List");
            int currentLine = 0;
            for (token tok : tokenList) {
                if (tok.getLineNumber() != currentLine) {
                    currentLine = tok.getLineNumber();
                    String numLead = String.format("%2d", currentLine);
                    System.out.print("\nLine " + numLead + ":\t");
                }

                System.out.print(tok + "\t");
            }
            System.out.println("\n\n");

            program program = parse(tokenList);
        }


    }

}
