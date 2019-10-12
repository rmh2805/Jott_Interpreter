package src.parseTree.tokens;

public class str_token extends token {
    private String data;

    public str_token(int lineNumber, String data) {
        super(lineNumber);
        this.data = data;
    }

    /**
     * Returns the string literal's data
     *
     * @return The string literal's data
     */
    public String toString() {
        return data;
    }
}
