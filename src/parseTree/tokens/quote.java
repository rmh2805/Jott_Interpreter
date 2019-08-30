package src.parseTree.tokens;

public class quote extends token {
    public quote(int lineNum) {
        super(lineNum);
    }

    public String toString() {
        return "\"";
    }
}
