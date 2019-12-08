package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.double_val;
import src.parseTree.tokens.double_token;
import src.parseTree.tokens.id;
import src.parseTree.tokens.op;
import src.parseTree.tokens.token;
import src.typeIdx;

public class double_expr extends expr<Double> implements double_val {
    private double_val lVal;
    private op operator;
    private double_val rVal;

    public void fixChildren() {
        if (lVal != null) return; // instances constructed with params have no children
        lVal = (double_val) children.get(0);

        if (children.size() == 2) {
            System.out.println("Error, double expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }
        if (children.size() == 3) {
            operator = (op) children.get(1);
            rVal = (double_val) children.get(2);
        }
    }

    public double_expr() {}

    @Override
    public Double execute() {
        this.fixChildren();
        double left = parseToken(lVal);

        if (operator == null)
            return left;

        double right = parseToken(rVal);

        switch (operator.toString()) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    errorPrinter.throwError(operator, new Runtime("Divide by Zero"));
                }
                return left / right;
            case "^":
                return Math.pow(left, right);
            default:
                errorPrinter.throwError(operator, new Runtime("Unrecognized operator: " + operator.toString()));
                return null;
        }
    }

    public int getLineNumber() {
        if (lVal instanceof token) return ((token) lVal).getLineNumber();
        return ((double_expr) lVal).getLineNumber();
    }

    public int getIndex() {
        if (lVal instanceof token) return ((token) lVal).getIndex();
        return ((double_expr) lVal).getIndex();
    }

    private double parseToken(double_val token) {
        if ((token instanceof double_expr)) {
            return ((double_expr) token).execute();
        }
        else if (token instanceof double_token) {
            return ((double_token) token).getVal();
        }
        else if (token instanceof f_call) {
            return (Double) ((f_call) token).execute();
        }
        else {
            id tok = (id) token;
            if (nameTableSingleton.getInstance().getType(tok) != typeIdx.k_Double)
                errorPrinter.throwError(tok, new Runtime("Error, attempt to use a non-double ID in a double expression"));
            return nameTableSingleton.getInstance().getDouble(tok);
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
