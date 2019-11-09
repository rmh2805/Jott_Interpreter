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
        int col = 1; //Start position of token
        List<token> tokenList = new LinkedList<>(); //The list of tokens which will be returned

        while (sc.hasNextLine()) {  //For each line of the source file
            String line = sc.nextLine();    //Grab the line
            int lineLength = line.length(); //Grab its length once

            for (int i = 0; i < lineLength; i++) { // Process every token on the line
                col = i + 1;
                StringBuilder tok = new StringBuilder();
                char ch = line.charAt(i);
                if (Character.isWhitespace(ch)) continue; // Skip whitespace before grabbing a new token
                if (ch == '/' && i + 1 < lineLength && line.charAt(i + 1) == '/') break; // Ignore comment
                if (Character.isAlphabetic(ch)) { // Identifier or keyword
                    while (i < lineLength) {
                        ch = line.charAt(i);
                        if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
                            i--;
                            break;
                        }
                        tok.append(ch);
                        i++;
                    }
                    // Check reserved keywords
                    switch (tok.toString()) {
                        case "print":
                            tokenList.add(new print_label(lineCount, col));
                            break;
                        case "concat":
                            tokenList.add(new concat_label(lineCount, col));
                            break;
                        case "charAt":
                            tokenList.add(new charAt_label(lineCount, col));
                            break;
                        case "Double":
                            tokenList.add(new double_label(lineCount, col));
                            break;
                        case "Integer":
                            tokenList.add(new int_label(lineCount, col));
                            break;
                        case "String":
                            tokenList.add(new str_label(lineCount, col));
                            break;
                        case "if":
                            tokenList.add(new if_label(lineCount, col));
                            break;
                        case "else":
                            tokenList.add(new else_label(lineCount, col));
                            break;
                        case "while":
                            tokenList.add(new while_label(lineCount, col));
                            break;
                        case "for":
                            tokenList.add(new for_label(lineCount, col));
                            break;
                        default:
                            if (Character.isLowerCase(tok.charAt(0)))
                                tokenList.add(new id(lineCount, col, tok.toString()));
                            else errorPrinter.throwError(lineCount, col, new Syntax("invalid type"));
                            break;
                    }
                } else if (ch == '"') { // str_literal
                    i++;
                    while (i < lineLength) {
                        ch = line.charAt(i);
                        if (ch == '"') break;
                        if (!Character.isAlphabetic(ch) && !Character.isDigit(ch) && ch != ' ')
                            errorPrinter.throwError(lineCount, col, new Syntax("Illegal character '" + ch +
                                    "' detected in string literal"));
                        tok.append(ch);
                        i++;
                    }
                    if (i == lineLength)
                        errorPrinter.throwError(lineCount, col, new Syntax("Strings cannot wrap lines"));
                    tokenList.add(new str_token(lineCount, col, tok.toString()));
                } else if (Character.isDigit(ch) || ch == '.') { // Integer or Double
                    boolean isDouble = false;
                    int intIn = -1;
                    double dbl = -1;
                    while (i < lineLength) {
                        ch = line.charAt(i);
                        if (ch == '.' && !isDouble) {
                            isDouble = true;
                            tok.append(ch);
                            i++;
                            continue;
                        }
                        if (!Character.isDigit(ch)) {
                            i--;
                            break;
                        }
                        tok.append(ch);
                        i++;
                    }
                    if (isDouble) {
                        try {
                            dbl = Double.parseDouble(tok.toString());
                            tokenList.add(new double_token(lineCount, col, dbl));
                        } catch (NumberFormatException e) {
                            errorPrinter.throwError(lineCount, col, new Syntax("invalid representation of a number"));
                        }
                    } else {
                        intIn = Integer.parseInt(tok.toString());
                        tokenList.add(new int_token(lineCount, col, intIn));
                    }
                } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') // op
                    tokenList.add(new op(lineCount, col, Character.toString(ch)));
                else if (ch == '>' || ch == '<' || ch == '=' || ch == '!') {
                    tok.append(ch);
                    if (i + 1 < lineLength) {
                        ch = line.charAt(++i);
                        if (ch == '=') tok.append(ch);
                        else i--;
                    }
                    switch (tok.toString()) {
                        case "!":
                            errorPrinter.throwError(lineCount, col, new Syntax("Malformed token"));
                            break;
                        case "=":
                            tokenList.add(new asmt_op(lineCount, col));
                            break;
                        default:
                            tokenList.add(new rel_op(lineCount, col, tok.toString()));
                            break;
                    }
                }
                else if (ch == '(') tokenList.add(new start_paren(lineCount, col));
                else if (ch == ')') tokenList.add(new end_paren(lineCount, col));
                else if (ch == '{') tokenList.add(new start_brace(lineCount, col));
                else if (ch == '}') tokenList.add(new end_brace(lineCount, col));
                else if (ch == ';') tokenList.add(new end_stmt(lineCount, col));
                else if (ch == ',') tokenList.add(new comma(lineCount, col));
                else errorPrinter.throwError(lineCount, col, new Syntax("Malformed token"));
            }
            lineCount++;
            col = lineLength;
        }

        tokenList.add(new EOF(lineCount - 1, col)); // append EOF on last line
        sc.close();
        return tokenList;
    }
}