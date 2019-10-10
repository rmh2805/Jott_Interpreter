package src.parseTree.tokens;

public abstract class token {
    private int lineNumber;
    private int index;

    public token (int lineNumber, int index) {
        this.lineNumber = lineNumber;
        this.index = index;
    }

    public int getLineNumber () {
        return lineNumber;
    }

    public int getIndex () {
        return index;
    }

    public abstract String toString();

}
