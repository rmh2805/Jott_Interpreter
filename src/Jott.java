package src;

import src.parseTree.nodes.program;
import src.parseTree.tokens.token;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static src.nameTableSingleton.init_nameTable;
import static src.parser.parse;

public class Jott {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Specify a file");
            System.exit(1);
        }

        init_nameTable(args[0]);
        List<token> tokenList = new LinkedList<>();
        try {
            tokenList = tokenizer.tokenize(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Specified Jott File: '" + args[0] + "not found");
            e.printStackTrace();
        }

        program root = parse(tokenList);
        root.fixChildren();
        root.execute();
    }
}
