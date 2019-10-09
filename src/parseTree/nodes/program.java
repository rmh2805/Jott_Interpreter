package src.parseTree.nodes;

public class program {
    private stmt_lst firstStatement;

    /**
     * Root node of the Jott parse tree
     */
    public program () {
        firstStatement = null;
    }

    /**
     * Sets the first statement on the statement list
     *
     * @param statement The first statement on the Jott statement list
     */
    public void setStatement (stmt_lst statement) {
        this.firstStatement = statement;
    }

    /**
     * Returns the first statement of the Jott statement list
     *
     * @return The first statement of the Jott statement list
     */
    public stmt_lst getStatement () {
        return this.firstStatement;
    }

    public void execute () {
        stmt_lst statement = firstStatement;

        while(statement != null) {
            statement.getStatement().execute();
            statement = statement.getNext();
        }
    }

    public String toString() {
        String acc = "";
        stmt_lst statement = firstStatement;

        while(statement != null) {
            acc = acc.concat(statement.toString()).concat("\n");
        }

        return acc;
    }

}
