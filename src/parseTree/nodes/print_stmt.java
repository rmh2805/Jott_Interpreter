package src.parseTree.nodes;

import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.print_label;
import src.parseTree.tokens.start_paren;

public class print_stmt extends stmt<Integer> {
    private print_label printLabel;
    private start_paren startParen;
    private expr toPrint;
    private end_paren endParen;

    /**
     * Create a new print statement
     *
     * @param printLabel The "Print" token
     * @param startParen The "(" token
     * @param toPrint    The expression whose value I should print
     * @param endParen   The ")" token
     */
    public print_stmt(print_label printLabel, start_paren startParen, expr toPrint, end_paren endParen) {
        if (printLabel == null || startParen == null || toPrint == null || endParen == null) {
            System.out.println("Error, missing a required token in print statement");
            System.exit(1);
        }

        this.printLabel = printLabel;
        this.startParen = startParen;
        this.toPrint = toPrint;
        this.endParen = endParen;
    }

    /**
     * Print return value of provided expression to console
     *
     * @param filePath The Jott source filepath
     * @return exit status (0 if successful)
     */
    public Integer execute(String filePath) {
        System.out.println(toPrint.execute(filePath));
        return 0;
    }

    public String toString() {
        return printLabel.toString() + startParen.toString() + toPrint.toString() + endParen.toString();
    }
}
