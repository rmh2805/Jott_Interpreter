package src.parseTree.tokens;

import src.parseTree.categories.Type;

public class str_label extends token implements Type {
    public str_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "String";
    }
}
