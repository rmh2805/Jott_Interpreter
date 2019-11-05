package src.parseTree.tokens;

public class for_label extends token {
    public for_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "for";
    }
}
