package src.parseTree.tokens;

public class return_label extends token {
    public return_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "return";
    }
}
