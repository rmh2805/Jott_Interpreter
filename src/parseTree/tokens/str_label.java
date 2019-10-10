package src.parseTree.tokens;

import src.parseTree.categories.type;

public class str_label extends token implements type {
    public str_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "String";
    }
}
