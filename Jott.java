import src.nameTableSingleton;
import src.parseTree.nodes.program;
import src.parseTree.tokens.token;
import src.tokenizer;

import java.io.FileNotFoundException;
import java.util.List;

import static src.parser.parse;

public class Jott {
    public static void main(String[] args) throws FileNotFoundException { // todo can we get rid of this exception?
        if (args.length != 1) { // check bad input length
            System.err.println("Correct usage: \n$java Jott {program.j}");
            return;
        }

        String filename = args[0];

        // Init the name table
        nameTableSingleton.init_nameTable(filename);

        // Tokenize the file
        List<token> tokenList = tokenizer.tokenize(filename);

        // Parse the tokens and execute
        program root = parse(tokenList);
        root.fixChildren();
        root.execute();
    }
}
