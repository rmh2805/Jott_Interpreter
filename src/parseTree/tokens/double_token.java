package src.parseTree.tokens;

import src.parseTree.nodes.value_types.double_val;

public class double_token extends token implements double_val {
    double val;

    public double_token(int lineNum, double val) {
        super(lineNum);
        this.val = val;
    }

    /**
     * Returns the value of the double token
     *
     * @return The value of the double token
     */
    public double getVal(String filePath) {
        return this.val;
    }

    public String toString() {
        return "" + this.val;
    }
}
