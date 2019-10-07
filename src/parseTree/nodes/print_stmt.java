package src.parseTree.nodes;

import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.end_stmt;
import src.parseTree.tokens.print_label;
import src.parseTree.tokens.start_paren;

import java.util.ArrayList;
import java.util.List;

public class print_stmt extends stmt<Integer> implements node {
    private print_label printLabel;
    private start_paren startParen;
    private expr toPrint;
    private end_paren endParen;
    private end_stmt endStmt;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        //todo Assign the proper children to their fields
    }

    public print_stmt() {}
    /**
     * Create a new print statement
     *
     * @param printLabel The "Print" token
     * @param startParen The "(" token
     * @param toPrint    The expression whose value I should print
     * @param endParen   The ")" token
     */
    public print_stmt(print_label printLabel, start_paren startParen, expr toPrint, end_paren endParen, end_stmt endStmt) {
        if (printLabel == null || startParen == null || toPrint == null || endParen == null) {
            System.out.println("Error, missing a required token in print statement");
            System.exit(1);
        }

        this.printLabel = printLabel;
        this.startParen = startParen;
        this.toPrint = toPrint;
        this.endParen = endParen;
        this.endStmt = endStmt;
    }

    /**
     * Print return value of provided expression to console
     *
     * @return exit status (0 if successful)
     */
    public Integer execute() {
        System.out.println(toPrint.execute());
        return 0;
    }

    public String toString() {
        return printLabel.toString() + startParen.toString() + toPrint.toString() + endParen.toString() + endStmt.toString();
    }
}
