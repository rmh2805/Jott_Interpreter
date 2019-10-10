package src.parseTree.tokens;

public class asmt_op extends token {
    public asmt_op(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "=";
    }
}
