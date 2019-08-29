package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class errorPrinter {
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
