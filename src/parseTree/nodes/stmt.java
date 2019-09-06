package src.parseTree.nodes;


import src.parseTree.tokens.end_stmt;

public abstract class stmt<RT> {
    protected end_stmt endStmt;

    public stmt(end_stmt endStmt) {
        if (endStmt == null) {
            System.out.println("Error, each statement must have an associated endStmt (\";\") token");
            System.exit(1);
        }

        this.endStmt = endStmt;
    }

    /**
     * Executes this statement properly
     *
     * @return Some return value (exit status or expression value)
     */
    public abstract RT execute();

    public end_stmt getEndStmt() {
        return endStmt;
    }

    /**
     * Print the content of this statement or evaluate a string expression
     *
     * @return The content/result received
     */
    public abstract String toString();
}
