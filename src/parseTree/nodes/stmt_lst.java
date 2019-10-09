package src.parseTree.nodes;

import java.util.ArrayList;
import java.util.List;
import src.parseTree.tokens.EOF;

public class stmt_lst implements node {
    private stmt statement;
    private stmt_lst next;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
        fixChildren();
    }

    public void fixChildren() {
        if (!(children.get(0) instanceof EOF)) {
            this.stmt_lst_set((stmt) children.get(0));
        }
    }

    public List<Object> getChildren() {
        return children;
    }

    public stmt_lst() {}
    /**
     * A node of the Jott statement list
     *
     * @param statement The statement to execute
     */
    public void stmt_lst_set(stmt statement) {
        if (statement == null) {
            System.out.println("Error, attempted to create a new statement list node without a statement and/or endStatement");
            System.exit(1);
        }

        this.statement = statement;
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
     * Returns the next node on the statement list
     *
     * @return The next node on the statement list (null if this is the last node)
     */
    public stmt_lst getNext() {
        return this.next;
    }

    /**
     * Prints the string representation of this statement
     *
     * @return the string representation of this statement
     */
    public String toString() {
        return statement.toString();
    }
}
