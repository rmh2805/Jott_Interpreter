package src.parseTree.tokens;

import src.parseTree.categories.Type;

public class double_label extends token implements Type {
    public double_label(int lineNumber, int index) {
        super(lineNumber, index);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
