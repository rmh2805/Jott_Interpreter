package src.tokenizer.tokens;

import src.parseTree.categories.Type;

public class double_label extends token implements Type {
    public double_label(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
