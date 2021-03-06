package src.parseTree.nodes;

public abstract class stmt<RT> extends node {

    /**
     * Executes this statement properly
     *
     * @return Some return value (exit status or expression value)
     */
    public abstract RT execute();

    /**
     * Print the content of this statement or evaluate a string expression
     *
     * @return The content/result received
     */
    public abstract String toString();
}
