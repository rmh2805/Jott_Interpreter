package src.parseTree.tokens;

import src.parseTree.categories.Type;

public class void_label extends token implements Type {
    public void_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "Void";
    }
}
