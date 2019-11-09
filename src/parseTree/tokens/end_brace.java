package src.parseTree.tokens;

public class end_brace extends token {
    public end_brace (int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString () {
        return "}";
    }
}
