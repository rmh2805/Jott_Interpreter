package src.parseTree.tokens;

import src.parseTree.nodes.value_types.double_val;
import src.parseTree.nodes.value_types.int_val;
import src.parseTree.nodes.value_types.str_val;

public class id extends token implements int_val, double_val, str_val {
    private String id;

    public id (int lineNum, String id) {
        super(lineNum);
        this.id = id;
    }

    @Override
    public String toString () {
        return id;
    }
}
