package src.parseTree.tokens;

public class charAt_label extends token {
    public charAt_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString() {
        return "charAt";
    }
}
