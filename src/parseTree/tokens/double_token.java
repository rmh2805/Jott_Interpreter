package src.parseTree.tokens;

import src.parseTree.categories.double_val;

public class double_token extends token implements double_val {
    double val;

    public double_token(int lineNumber, int index, double val) {
        super(lineNumber, index);
        this.val = val;
    }

    /**
     * Returns the value of the double token
     *
     * @return The value of the double token
     */
    public double getVal() {
        return this.val;
    }

    public void negate() {
        val *= -1;
    }

    public String toString() {
        return "" + this.val;
    }
}
