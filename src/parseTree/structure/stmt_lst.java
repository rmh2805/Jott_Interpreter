package src.parseTree.structure;

import src.parseTree.nodes.stmt;
import src.parseTree.tokens.end_stmt;

public class stmt_lst {
    private stmt statement;
    private end_stmt endStmt;
    private stmt_lst next;

    /**
     * A node of the Jott statement list
     *
     * @param statement The statement to execute
     * @param endStmt   The ";" token marking the end of this statement
     */
    public stmt_lst(stmt statement, end_stmt endStmt) {
        if (statement == null || endStmt == null) {
            System.out.println("Error, attempted to create a new statement list node without a statement and/or endStatement");
            System.exit(1);
        }

        this.statement = statement;
        this.endStmt = endStmt;
        this.next = null;
    }

    /**
     * Set the next node on the statement list
     * @param next The next node on the statement list
     */
    public void setNextStatement(stmt_lst next) {
        this.next = next;
    }

    /**
     * Returns the statement this node represents
     *
     * @return The statement attached to this node
     */
    public stmt getStatement() {
        return this.statement;
    }

    /**
     * Returns the semicolon token marking the end of this statement
     *
     * @return This statement's semicolon terminator
     */
    public end_stmt getEndStmt() {
        return this.endStmt;
    }

    /**
     * Returns the next node on the statement list
     *
     * @return The next node on the statement list (null if this is the last node)
     */
    public stmt_lst getNext() {
        return this.next;
    }

    /**
     * Prints the string representation of this
     *
     * @return
     */
    public String toString() {
        return statement.toString() + endStmt.toString() + '\n';
    }
}
