package src.parseTree.tokens;

public class if_label extends token {
    public if_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "if";
    }
}
