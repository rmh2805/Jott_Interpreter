package src.parseTree.nodes;

import src.parseTree.categories.double_val;
import src.parseTree.tokens.op;

public class double_expr extends expr<Double> implements double_val {
    private double_val lVal;
    private op operator;
    private double_val rVal;

    public double_expr(double_val lVal, op operator, double_val rVal) {
        this.lVal = lVal;
        this.operator = operator;
        this.rVal = rVal;
    }

    @Override
    public Double execute(String filePath) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
