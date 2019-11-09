package src.parseTree.tokens;

public class rel_op extends token {
    private String operator;

    public rel_op (int lineNumber, int index, String operator) {
        super(lineNumber, index);
        this.operator = operator;
    }

    public rel_op (int lineNumber, int index, char operator) {
        this(lineNumber, index, ""+operator);
    }

    @Override
    public String toString() {
        return operator;
    }
}
