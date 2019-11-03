import java.io.FileNotFoundException;
import java.util.List;

import src.nameTableSingleton;
import src.parser;
import src.tokenizer;
import src.parseTree.nodes.program;
import src.parseTree.tokens.token;

public class Jott {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Correct usage: \n$java Jott {program.j}");
            return;
        }

        String filename = args[0];

        // Init the name table
        nameTableSingleton.init_nameTable(filename);


        try {
            // Tokenize the file
            List<token> tokenList = tokenizer.tokenize(filename);

            // Parse the tokens
            program root = parser.parse(tokenList);

            // Execute
            root.execute();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Specified Jott File: '" + filename + "not found");
            e.printStackTrace();
        }

    }
}
