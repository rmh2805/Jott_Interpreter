package src.parseTree.tokens;

public class double_token extends token {
    double val;

    public double_token(int lineNum, double val) {
        super(lineNum);
        this.val = val;
    }

    public double getVal() {
        return this.val;
    }

    public String toString() {
        return "" + this.val;
    }
}
