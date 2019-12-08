package src.errorHandling;

import src.errorHandling.types.abstract_error;
import src.nameTableSingleton;
import src.parseTree.tokens.token;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class errorPrinter {

    /**
     * Prints out a standardized error message for a token
     *
     * @param token The token throwing the error
     * @param error An object that represents an error
     */
    public static void throwError(token token, abstract_error error) {
        throwError(token.getLineNumber(), token.getIndex(), error);
    }

    /**
     * Prints out a standardized error message
     *
     * @param lineNum The line the error was found on
     * @param index   The index on the line the error was found on
     * @param error   An object that represents an error
     */
    public static void throwError(int lineNum, int index, abstract_error error) {
        String filePath = nameTableSingleton.getFilePath();
        String line;

        try {
            Scanner sc = new Scanner(new File(filePath));
            for (int i = 1; i < lineNum; i++)
                sc.nextLine();

            line = sc.nextLine();

        } catch (IOException e) {
            line = "Unable to grab a source line for the Jott error check. The above is the Java Stacktrace.";
            e.printStackTrace();
        }

        System.out.flush();
        System.err.printf(error.toString(), line, filePath, lineNum, index);
        System.exit(1);
    }
}
