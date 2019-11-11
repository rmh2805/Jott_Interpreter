import src.nameTableSingleton;
import src.parseTree.nodes.program;
import src.tokenizer.tokens.token;
import src.tokenizer.tokenizer;

import java.io.FileNotFoundException;
import java.util.List;

import static src.parser.parse;

public class Jott {
    public static void main(String[] args) {
        if (args.length != 1) { // check bad input length
            System.err.println("Correct usage: \n$java Jott {program.j}");
            return;
        }

        String filename = args[0];

        // Init the name table
        nameTableSingleton.init_nameTable(filename);


        try {
            // Tokenize the file
            List<token> tokenList = tokenizer.tokenize(args[0]);

            // Parse the tokens and execute
            program root = parse(tokenList);
            root.execute();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Specified Jott File: '" + args[0] + "not found");
            e.printStackTrace();
        }

    }
}
