package src.parseTree.tokens;

import src.parseTree.categories.int_val;

public class int_token extends token implements int_val {
    private int val;

    public int_token(int lineNumber, int index, int val) {
        super(lineNumber, index);
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

    public void negate() {
        val *= -1;
    }

    public String toString() {
        return "" + val;
    }
}
