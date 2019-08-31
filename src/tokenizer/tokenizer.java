package src.tokenizer;

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
                    System.out.print("\nLine " + currentLine + ":\t");
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
            System.out.print(lineCount + ":");

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
                else if ("*".equals(tok.toString()) || "/".equals(tok.toString()) || "^".equals(tok.toString())) {
                    tokenList.add(new op(lineCount, tok.toString()));
                }
                //TODO: Check for string literals, numerical literals, ids, and '+' and '-' operators

                System.out.print(tok.toString() + '\t');
            }

            System.out.print('\n');

            //Grab the token

            //
            //            for (int i = 0; i < lineLength; i++) {  //Iterate along the line (no token will cross lines)
            //                char ch = line.charAt(i);
            //
            //                //=========================<Skip Whitespace>=========================//
            //                if (Character.isWhitespace(ch)) {   //Ignore the whitespace
            //                    continue;
            //                }
            //
            //                //=======================<Assignment Operator>=======================//
            //                if (ch == '=' && i + 1 < lineLength) {
            //                    if (Character.isWhitespace(line.charAt(i + 1))) {
            //                        tokenList.add(new asmt_op(lineCount));
            //                        continue;
            //                    }
            //                }
            //                else if (ch == '=' && i + 1 == lineLength) {
            //                    tokenList.add(new asmt_op(lineLength));
            //                    continue;
            //                }
            //
            //                //==================<Grab Sign Operators & Numbers>==================//
            //                if (ch == '-' || ch == '+') {
            //                    if (i + 1 == lineLength) {
            //                        tokenList.add(new op(lineCount, ch));
            //                        continue;
            //                    }
            //                }
            //
            //                //=================<Grab Numbers or Sign Operators>==================//
            //
            //                /*
            //                else if (ch == '+' || ch == '*' || ch == '=' || ch == '-' || ch == '^' || ch == '/'
            //                        || ch == ';' || ch == '(' || ch == ')') {   //All single operators
            //                    //tokenList.add(new token(ch, lineCount));
            //                }
            //                else if (ch == '"') {   //String literal
            //                    //tokenList.add(new token("\"", lineCount));  //Start of a String
            //
            //                    //Read the rest of the string
            //                    i++;
            //                    ch = line.charAt(i);
            //                    while (ch != '"') {
            //                        if (!Character.isAlphabetic(ch) && (ch < '0' || ch > '9') && ch != ' ') {
            //                            errorPrinter.printSyntaxError(lineCount, filepath, "Illegal character in string," +
            //                                    " only allowed characters are alphanumeric and spaces");
            //                        }
            //
            //                        tok += ch;
            //                        i++;
            //                        if (i >= lineLength) {
            //                            errorPrinter.printSyntaxError(lineCount, filepath, "Expected a close quote before the end of the line");
            //                        }
            //
            //                        ch = line.charAt(i);
            //                    }
            //                    //tokenList.add(new token(tok, lineCount));
            //                    //tokenList.add(new token("\"", lineCount));
            //
            //                    continue;
            //                }
            //                else if (ch >= '0' && ch <= '9') {  //Digits
            //                    tok += ch;
            //                    i++;
            //                    if (i < lineLength) {
            //                        ch = line.charAt(i);
            //                        while (ch >= '0' && ch <= '9') { //Get all left side digits
            //                            tok += ch;
            //                            i++;
            //                            ch = line.charAt(i);
            //                        }
            //                        if (ch == '.') { //Grab Decimal
            //                            tok += ch;
            //                            i++;
            //
            //                            if (i < lineLength) {
            //                                ch = line.charAt(i);
            //                                while (ch >= '0' && ch <= '9') { //Get all left side digits
            //                                    tok += ch;
            //                                    i++;
            //                                    if (i >= lineLength)
            //                                        break;
            //                                    ch = line.charAt(i);
            //                                }
            //                            }
            //                        }
            //                    }
            //
            //                    if (i < lineLength)
            //                        if (!Character.isWhitespace(ch) && ch != ';')
            //                            errorPrinter.printSyntaxError(lineCount, filepath, "Token not a number");
            //                    i--;
            //                    //tokenList.add(new token(tok, lineCount));
            //                }
            //                else if (ch == '.') {
            //                    tok += ch;
            //                    i++;
            //
            //                    if (i < lineLength) {
            //                        ch = line.charAt(i);
            //                        while (ch >= '0' && ch <= '9') { //Get all left side digits
            //                            tok += ch;
            //                            i++;
            //                            if (i >= lineLength)
            //                                break;
            //                            ch = line.charAt(i);
            //                        }
            //                    }
            //
            //                    if (i < lineLength)
            //                        if (!Character.isWhitespace(ch) && ch != ';')
            //                            errorPrinter.printSyntaxError(lineCount, filepath, "Token not a number");
            //                    i--;
            //                    //tokenList.add();
            //                }
            //                */
            //            }

            lineCount++;
        }


        return tokenList;
    }
}