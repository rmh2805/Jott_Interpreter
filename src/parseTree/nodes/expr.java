package src.parseTree.nodes;

import src.parseTree.tokens.end_stmt;

public abstract class expr<T> extends stmt<T> {
    public expr(end_stmt endStmt) {
        super(endStmt);
    }

    /**
     * Evaluates this expression
     *
     * @param filePath Source filepath
     * @return the return value of execute
     */
    public abstract T execute(String filePath);

    /**
     * evaluates the expression and returns its value as a String
     *
     * @return a string representation of this expression
     */
    public abstract String toString();
}
