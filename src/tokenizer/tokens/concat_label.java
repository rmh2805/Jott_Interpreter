package src.tokenizer.tokens;

public class concat_label extends token {

    public concat_label(int lineNumber) {
        super(lineNumber);
    }

    public String toString() {
        return "concat";
    }
}
