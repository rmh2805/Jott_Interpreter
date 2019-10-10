package src.parseTree.tokens;

public class quote extends token {
    public quote(int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString() {
        return "\"";
    }
}
