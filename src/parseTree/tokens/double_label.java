package src.parseTree.tokens;

import src.parseTree.categories.type;

public class double_label extends token implements type {
    public double_label(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
