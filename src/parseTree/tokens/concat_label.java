package src.parseTree.tokens;

public class concat_label extends token {

    public concat_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString() {
        return "concat";
    }
}
