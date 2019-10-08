package src.parseTree.nodes;

import java.util.ArrayList;
import java.util.List;

public class program implements node {
    private stmt_lst firstStatement;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        //todo Assign the proper children to their fields
    }

    public List<Object> getChildren() {
        return children;
    }

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

}
