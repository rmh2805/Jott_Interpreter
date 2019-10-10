package src.parseTree.tokens;

public class comma extends token {
    public comma(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return ",";
    }
}
