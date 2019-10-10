package src.parseTree.tokens;

import src.parseTree.categories.type;

public class int_label extends token implements type {
    public int_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString() {
        return "Integer";
    }
}
