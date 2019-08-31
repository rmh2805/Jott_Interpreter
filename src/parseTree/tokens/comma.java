package src.parseTree.tokens;

public class comma extends token {
    public comma(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return ",";
    }
}
