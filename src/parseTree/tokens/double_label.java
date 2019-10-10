package src.parseTree.tokens;

import src.parseTree.categories.type;

public class double_label extends token implements type {
    public double_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
