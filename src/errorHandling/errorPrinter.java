package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class errorPrinter {
    /**
     * Prints out a standardized class of syntax error message
     *
     * @param lineNum  The line the error was found on
     * @param filePath The filepath of the Jott source
     * @param Remedy   A more detailed description as to the nature of the error
     */
    public static void printSyntaxError(int lineNum, String filePath, String Remedy) {
        System.out.print("\nSyntax Error: line ");
        System.out.print(lineNum);
        System.out.print(" '");
        try {
            Scanner sc = new Scanner(new File(filePath));
            for (int i = 1; i < lineNum; i++)
                sc.nextLine();
            System.out.print(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("'. " + Remedy);
        System.exit(1);
    }
}
