package src.parseTree.tokens;

import src.parseTree.categories.int_val;

public class int_token extends token implements int_val {
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
    public int getVal(String filePath) {
        return val;
    }

    public String toString() {
        return "" + val;
    }
}
