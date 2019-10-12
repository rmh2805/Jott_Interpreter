package src.parseTree.nodes;

import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.end_stmt;
import src.parseTree.tokens.print_label;
import src.parseTree.tokens.start_paren;

public class print_stmt extends stmt<Integer> {
    private print_label printLabel;
    private start_paren startParen;
    private expr toPrint;
    private end_paren endParen;
    private end_stmt endStmt;

    public void fixChildren() {
        //todo Add some validation
        this.printLabel = (print_label) children.get(0);
        this.startParen = (start_paren) children.get(1);
        this.toPrint = (expr) children.get(2);
        this.endParen = (end_paren) children.get(3);
        this.endStmt = (end_stmt) children.get(4);
    }

    /**
     * Print return value of provided expression to console
     *
     * @return exit status (0 if successful)
     */
    public Integer execute() {
        this.fixChildren();
        System.out.println(toPrint.execute());
        return 0;
    }

    public String toString() {
        return printLabel.toString() + startParen.toString() + toPrint.toString() + endParen.toString() + endStmt.toString();
    }
}
