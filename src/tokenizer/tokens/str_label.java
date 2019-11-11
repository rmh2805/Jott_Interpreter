package src.tokenizer.tokens;

import src.parseTree.categories.Type;

public class str_label extends token implements Type {
    public str_label(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "String";
    }
}
