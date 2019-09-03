package src.parseTree.nodes;

import src.parseTree.tokens.end_stmt;
import src.parseTree.tokens.int_token;
import src.parseTree.tokens.op;

public class int_expr extends expr<Integer> implements int_val {
    private int_val lVal;
    private op operator;
    private int_val rVal;

    public int_expr(int_val lVal, op operator, int_val rVal, end_stmt endStmt) {
        super(endStmt);

        if (lVal == null || (operator != null && rVal == null) || (operator == null && rVal != null)) {
            System.out.println("Error, int expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }

        this.lVal = lVal;
        this.operator = operator;
        this.rVal = rVal;
    }

    @Override
    public Integer execute(String filePath) {
        int left;
        if ((lVal instanceof int_expr)) {
            left = ((int_expr) lVal).getVal(filePath);
        }
        else if (lVal instanceof int_token) {
            left = ((int_token) lVal).getVal(filePath);
        }
        else {
            //TODO: Parse the ID to value
            left = 0;
        }

        if (operator == null)
            return left;

        int right;

        if ((rVal instanceof int_expr)) {
            right = ((int_expr) rVal).getVal(filePath);
        }
        else if (rVal instanceof int_token) {
            right = ((int_token) rVal).getVal(filePath);
        }
        else {
            //TODO: Parse the ID to value
            System.out.println("Note: unable to parse IDs yet");
            right = 0;
        }

        switch (operator.toString()) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                return left / right;
            case "^":
                return (int) java.lang.Math.pow(left, right);
            default:
                //TODO: Runtime error here for unrecognized operator
                return null;
        }
    }

    @Override
    public String toString() {
        return lVal.toString() + operator.toString() + rVal.toString() + endStmt.toString();
    }

    private int getVal(String filePath) {
        return execute(filePath);
    }
}
