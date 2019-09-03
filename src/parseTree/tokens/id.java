package src.parseTree.tokens;

import src.parseTree.nodes.double_val;
import src.parseTree.nodes.int_val;
import src.parseTree.nodes.str_val;

public class id extends token implements int_val, double_val, str_val {
    private String id;

    public id (int lineNum, String id) {
        super(lineNum);
        this.id = id;
    }


    public String toString () {
        return id;
    }
}
