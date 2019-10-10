package src.parseTree.nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class node {
    protected List<Object> children = new ArrayList<>();

    /**
     * Adds a child to the node's internal list of children
     *
     * @param child The child to add to the node
     */
    public void addChild(Object child) {
        children.add(child);
    }

    public List<Object> getChildren() {
        return children;
    }

    /**
     * Assign children to their fields for execution
     */
    public abstract void fixChildren();
}
