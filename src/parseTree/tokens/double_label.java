package src.parseTree.tokens;

public class double_label extends token {
    public double_label(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
