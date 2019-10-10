package src.parseTree.tokens;

public class EOF extends token {

    public EOF (int lineNum) {
        super(lineNum);
    }

    @Override
    public String toString() {
        return "";
    }
}
