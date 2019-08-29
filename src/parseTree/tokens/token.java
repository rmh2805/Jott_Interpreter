package src.parseTree.tokens;

public abstract class token {
    private int lineNumber;

    public token (int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber () {
        return lineNumber;
    }

}
