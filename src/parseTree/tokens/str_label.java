package src.parseTree.tokens;

public class str_label extends token {
    public str_label(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "String";
    }
}
