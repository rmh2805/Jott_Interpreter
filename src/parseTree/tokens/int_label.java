package src.parseTree.tokens;

public class int_label extends token {
    public int_label(int lineNumber) {
        super(lineNumber);
    }

    public String toString() {
        return "Integer";
    }
}
