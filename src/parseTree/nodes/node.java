package src.parseTree.nodes;

import java.util.List;

public interface node {
    /**
     * Adds a child to the node's internal list of children
     *
     * @param child The child to add to the node
     */
    public void addChild(Object child);

    public List<Object> getChildren();

    /**
     * Assign children to their fields for execution
     */
    public void fixChildren();
}
