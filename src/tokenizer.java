package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.tokens.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class tokenizer {

    /**
     * Tokenizes the provided Jott source file
     *
     * @param filepath The filepath of the Jott source file to parse
     * @return The ordered list of tokens generated from the Jott source
     * @throws FileNotFoundException Thrown iff the Jott source file cannot be found
     */
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
                            errorPrinter.throwError(lineCount, new Syntax("Illegal character '" + ch +
                                    "' detected in string literal"));

                        tok.append(ch);

                        i++;
                    }

                    if (i == lineLength)
                        errorPrinter.throwError(lineCount, new Syntax("Strings cannot wrap lines"));

                    tokenList.add(new str_token(lineCount, tok.toString()));
                    tokenList.add(new quote(lineCount));
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
                            errorPrinter.throwError(lineCount, new Syntax("invalid representation of a number"));
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
                            errorPrinter.throwError(lineCount, new Syntax("invalid character in variable/function identifier"));
                    }
                    tokenList.add(new id(lineCount, tok.toString()));
                }
                else {
                    errorPrinter.throwError(lineCount, new Syntax("Malformed token at index "
                            + (i - tok.length() + 1)));
                }

            }

            lineCount++;
        }


        return tokenList;
    }
}