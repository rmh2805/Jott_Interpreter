package src.parseTree.tokens;

public class int_token extends token {
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
    public int getVal() {
        return val;
    }

    public String toString() {
        return "" + val;
    }
}
