package src.tokenizer.tokens;

public abstract class token {
    private int lineNumber;

    public token (int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber () {
        return lineNumber;
    }

    public abstract String toString();

}
