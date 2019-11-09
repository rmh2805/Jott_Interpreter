package src.parseTree.tokens;

public class while_label extends token {
    public while_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "while";
    }
}
