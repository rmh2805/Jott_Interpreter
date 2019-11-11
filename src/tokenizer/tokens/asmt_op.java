package src.tokenizer.tokens;

public class asmt_op extends token {
    public asmt_op(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "=";
    }
}
