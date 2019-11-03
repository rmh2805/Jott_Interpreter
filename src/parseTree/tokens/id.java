package src.parseTree.tokens;

import src.parseTree.categories.double_val;
import src.parseTree.categories.int_val;
import src.parseTree.categories.str_val;

public class id extends token implements int_val, double_val, str_val {
    private String id;

    public id (int lineNumber, int index, String id) {
        super(lineNumber, index);
        this.id = id;
    }

    public void fixChildren() {

    }

    public String toString () {
        return id;
    }
}
