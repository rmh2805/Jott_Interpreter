package src.tokenizer.tokens;

import src.parseTree.categories.Type;

public class int_label extends token implements Type {
    public int_label(int lineNumber) {
        super(lineNumber);
    }

    public String toString() {
        return "Integer";
    }
}
