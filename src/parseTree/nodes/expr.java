package src.parseTree.nodes;

public abstract class expr<T> extends b_stmt<T> {
    public int getLineNumber() {
        return 0;
    }

    public int getIndex() {
        return 0;
    }
}
