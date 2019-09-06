package src.parseTree.tokens;

import src.parseTree.nodes.value_types.int_val;
import src.parseTree.nodes.value_types.value;

public class int_token extends token implements int_val<Integer>, value<Integer> {
    private int val;

    public int_token(int lineNum, int val) {
        super(lineNum);
        this.val = val;
    }

    /**
     * Returns the value of the integer token
     *
     * @return The value of the integer token
     */
    public Integer getValue() {
        return val;
    }

    public String toString() {
        return "" + val;
    }
}
