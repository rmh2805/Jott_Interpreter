package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.int_val;
import src.parseTree.tokens.id;
import src.parseTree.tokens.int_token;
import src.parseTree.tokens.op;
import src.parseTree.tokens.token;
import src.typeIdx;

public class int_expr extends expr<Integer> implements int_val {
    private int_val lVal;
    private op operator;
    private int_val rVal;

    public void fixChildren() {
        if (lVal != null) return; // instances constructed with params have no children
        lVal = (int_val) children.get(0);

        if (children.size() == 2) {
            System.out.println("Error, int expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }
        if (children.size() == 3) {
            operator = (op) children.get(1);
            rVal = (int_val) children.get(2);
        }
    }

    public int_expr() {}

    private int_expr(int_val lVal, op operator, int_val rVal) {
        if (lVal == null || (operator != null && rVal == null) || (operator == null && rVal != null)) {
            System.out.println("Error, int expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }

        this.lVal = lVal;
        this.operator = operator;
        this.rVal = rVal;
    }

    @Override
    public Integer execute() {
        this.fixChildren();
        int left = parseToken(lVal);
        int right = 0;

        if (operator == null)
            return left;

        //in the case that there are more than 2 values to compute: compute the first two, obtain the results, create a new
        //int_expr containing the result, next operand, and value following the operand. This is recursively done until
        //computation ends at the last value
        if (rVal instanceof int_expr && ((int_expr) rVal).children.size() == 3) {
            int_expr intExp = new int_expr(lVal, operator, (int_val) (((int_expr) rVal).children.get(0)));
            left = intExp.execute();
            int_val leftValue = new int_token(((token) lVal).getLineNumber(), left);
            operator = (op) ((int_expr) rVal).children.get(1);
            int_expr expr = new int_expr(leftValue, operator, ((int_expr) (((int_expr) rVal).children.get(2))));
            return expr.execute();
        } else {
            right = parseToken(rVal);
        }
        switch (operator.toString()) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    errorPrinter.throwError(operator.getLineNumber(), new Runtime("Divide by Zero"));
                }
                return left / right;
            case "^":
                return (int) java.lang.Math.pow(left, right);
            default:
                errorPrinter.throwError(operator.getLineNumber(), new Runtime("Unrecognized operator: " + operator.toString()));
                return null;
        }
    }

    public int getLineNumber() {
        if (lVal instanceof int_expr)
            return ((int_expr) lVal).getLineNumber();
        else
            return ((token) lVal).getLineNumber();
    }

    private int parseToken(int_val token) {
        if ((token instanceof int_expr)) {
            return ((int_expr) token).execute();
        }
        else if (token instanceof int_token) {
            return ((int_token) token).getValue();
        }
        else {
            id tok = (id) token;
            if (nameTableSingleton.getInstance().getType(tok) != typeIdx.k_Integer)
                errorPrinter.throwError(tok.getLineNumber(), new Runtime("Error, attempt to use a non-int ID in an int expression"));
            return nameTableSingleton.getInstance().getInt(tok);
        }

    }

    @Override
    public String toString() {
        if (operator != null && rVal != null) {
            return lVal.toString() + operator.toString() + rVal.toString();
        } else {
            return lVal.toString();
        }
    }
}
