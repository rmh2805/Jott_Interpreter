package src.parseTree.tokens;

public class EOF extends token {

    public EOF (int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "";
    }
}
