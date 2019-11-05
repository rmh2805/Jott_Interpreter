package src.parseTree.tokens;

public class else_label extends token {
    public else_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "else";
    }
}
