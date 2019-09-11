package src.errorHandling;

import src.errorHandling.types.abstract_error;
import src.nameTableSingleton;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class errorPrinter {
    /**
     * Prints out a standardized error message
     *
     * @param lineNum  The line the error was found on
     * @param error    An object that represents an error
     */
    public static void throwError(int lineNum, abstract_error error) {
        String filePath = nameTableSingleton.getFilePath();
        String line = "Something went wrong. Printing Java Stacktrace above.";
        try {
            Scanner sc = new Scanner(new File(filePath));
            for (int i = 1; i < lineNum; i++)
                sc.nextLine();
            line = sc.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.err.printf(error.toString(), line, lineNum);
        System.exit(1);
    }
}