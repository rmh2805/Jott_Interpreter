package src.parseTree.nodes;

import src.parseTree.categories.str_val;

public abstract class str_expr extends expr<String> implements str_val {
    public int getLineNumber() {
        return 0;
    }

    public int getIndex() {
        return 0;
    }

}
