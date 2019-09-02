package src.tokenizer;

import src.errorPrinter;
import src.parseTree.tokens.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class tokenizer {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Error: Specify a file to tokenize");
            System.exit(1);
        }
        else {
            List<token> tokenList = tokenize(args[0]);

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
        }


    }

    public static List<token> tokenize(String filepath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filepath));
        int lineCount = 1;  //An incrementing line counter
        List<token> tokenList = new LinkedList<>(); //The list of tokens which will be returned

        while (sc.hasNextLine()) {  //For each line of the source file
            String line = sc.nextLine();    //Grab the line
            int lineLength = line.length(); //Grab its length once

            for (int i = 0; i < lineLength; i++) {
                //Repeat these actions while there are still chars on the line
                StringBuilder tok = new StringBuilder();

                //Clear whitespace to start of new token
                while (i < lineLength) {
                    char ch = line.charAt(i);
                    if (!Character.isWhitespace(ch))
                        break;
                    i++;
                }

                while (i < lineLength) {    //Tokens end at the end of a line
                    char ch = line.charAt(i);
                    if (Character.isWhitespace(ch))  //Tokens also end on whitespace
                        break;

                    if (ch == ';' || ch == '(' || ch == ')' || ch == ',' || ch == '"') {
                        if (!tok.toString().isEmpty()) {
                            i--;
                            break;
                        }
                        else {
                            tok.append(ch);
                            break;
                        }
                    }
                    tok.append(ch);

                    i++;
                }

                //Handle the gathered token
                if ("=".equals(tok.toString())) {
                    tokenList.add(new asmt_op(lineCount));
                }
                else if ("charAt".equals(tok.toString())) {
                    tokenList.add(new charAt_label(lineCount));
                }
                else if (",".equals(tok.toString())) {
                    tokenList.add(new comma(lineCount));
                }
                else if ("concat".equals(tok.toString())) {
                    tokenList.add(new concat_label(lineCount));
                }
                else if ("Double".equals(tok.toString())) {
                    tokenList.add(new double_label(lineCount));
                }
                else if (")".equals(tok.toString())) {
                    tokenList.add(new end_paren(lineCount));
                }
                else if (";".equals(tok.toString())) {
                    tokenList.add(new end_stmt(lineCount));
                }
                else if ("Integer".equals(tok.toString())) {
                    tokenList.add(new int_label(lineCount));
                }
                else if ("print".equals(tok.toString())) {
                    tokenList.add(new print_label(lineCount));
                }
                else if ("(".equals(tok.toString())) {
                    tokenList.add(new start_paren(lineCount));
                }
                else if ("String".equals(tok.toString())) {
                    tokenList.add(new str_label(lineCount));
                }
                else if ("*".equals(tok.toString()) || "/".equals(tok.toString()) || "^".equals(tok.toString()) ||
                        "+".equals(tok.toString()) || "-".equals(tok.toString())) {
                    tokenList.add(new op(lineCount, tok.toString()));
                }
                else if ("\"".equals(tok.toString())) {
                    //String literal
                    tokenList.add(new quote(lineCount));

                    tok = new StringBuilder();

                    i++;
                    while (i < lineLength) {
                        char ch = line.charAt(i);
                        if (ch == '"')
                            break;

                        if (!((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == ' '))
                            errorPrinter.printSyntaxError(lineCount, filepath, "Illegal character '" + ch +
                                    "' detected in string literal");

                        tok.append(ch);

                        i++;
                    }

                    if (i == lineLength)
                        errorPrinter.printSyntaxError(lineCount, filepath, "Strings cannot wrap lines");

                    tokenList.add(new str_token(lineCount, tok.toString()));
                    tokenList.add(new quote(lineCount));

                    tok = new StringBuilder();
                    tok.append('"');

                }
                else if (tok.charAt(0) == '+' || tok.charAt(0) == '-' || tok.charAt(0) == '.' || Character.isDigit(tok.charAt(0))) {
                    boolean isDouble = false;
                    int intV = -1;
                    double dblV = -1;
                    try {
                        intV = Integer.parseInt(tok.toString());
                    } catch (NumberFormatException n) {
                        try {
                            dblV = Double.parseDouble(tok.toString());
                            isDouble = true;
                        } catch (NumberFormatException m) {
                            errorPrinter.printSyntaxError(lineCount, filepath, "invalid representation of a number");
                        }
                    }

                    if (isDouble) {
                        tokenList.add(new double_token(lineCount, dblV));
                    }
                    else {
                        tokenList.add(new int_token(lineCount, intV));
                    }

                }
                else if (Character.isLowerCase(tok.charAt(0))) {
                    for (int j = 1; j < tok.length(); j++) {
                        char ch = tok.charAt(j);
                        if (!Character.isAlphabetic(ch) && !Character.isDigit(ch))
                            errorPrinter.printSyntaxError(lineCount, filepath, "invalid character in variable/function identifier");
                    }
                    tokenList.add(new id(lineCount, tok.toString()));
                }
                else {
                    errorPrinter.printSyntaxError(lineCount, filepath, "Malformed token at index "
                            + (i - tok.length() + 1));
                }

            }

            lineCount++;
        }


        return tokenList;
    }
}