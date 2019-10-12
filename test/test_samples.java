package test;

import src.parseTree.nodes.program;
import src.parseTree.tokens.token;
import src.tokenizer;

import java.io.FileNotFoundException;
import java.util.List;

import static src.nameTableSingleton.init_nameTable;
import static src.parser.parse;

public class test_samples {
    public static void main(String[] args) throws FileNotFoundException {
        for (int i = 0; i <= 23; i++) {  //For each of the sample files
            switch (i) {
                case 3:
                case 4:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                    continue;

                default:
            }
            System.out.println("\n\n\t\t\t====<file " + i + ">====");
            String fileName = String.format("jottExamples/sample_inputs_phase1/prog%d.j", i);
            init_nameTable(fileName);
            List<token> tokenList = tokenizer.tokenize(fileName);

            program root = parse(tokenList);
            root.execute();
        }
    }
}
