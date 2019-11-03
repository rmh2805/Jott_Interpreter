package src.parseTree.tokens;

import src.parseTree.categories.Type;

public class int_label extends token implements Type {
    public int_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString() {
        return "Integer";
    }
}
