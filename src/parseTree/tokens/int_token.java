package src.parseTree.tokens;

public class int_token extends token {
    int val;

    public int_token(int lineNum, int val) {
        super(lineNum);
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return "" + val;
    }
}
