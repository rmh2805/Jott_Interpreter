package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.double_val;
import src.tokenizer.tokens.double_token;
import src.tokenizer.tokens.id;
import src.tokenizer.tokens.op;
import src.tokenizer.tokens.token;
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

    private double_expr(double_val lVal, op operator, double_val rVal) {
        if (lVal == null || (operator != null && rVal == null) || (operator == null && rVal != null)) {
            System.out.println("Error, double expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }
        this.lVal = lVal;
        this.operator = operator;
        this.rVal = rVal;
    }

    @Override
    public Double execute() {
        this.fixChildren();
        double left = parseToken(lVal);
        double right = 0;

        if (operator == null)
            return left;

        //in the case that there are more than 2 values to compute: compute the first two, obtain the results, create a new
        //double_expr containing the result, next operand, and value following the operand. This is recursively done until
        //computation ends at the last value
        if (rVal instanceof double_expr && ((double_expr) rVal).children.size() == 3) {
            double_expr doubleExpr = new double_expr(lVal, operator, (double_val) ((double_expr) rVal).children.get(0));
            left = doubleExpr.execute();
            double_val leftValue = new double_token(((token) lVal).getLineNumber(), left);
            operator = (op) ((double_expr) rVal).children.get(1);
            double_expr expr = new double_expr(leftValue, operator, ((double_expr) (((double_expr) rVal).children.get(2))));
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
                return Math.pow(left, right);
            default:
                errorPrinter.throwError(operator.getLineNumber(), new Runtime("Unrecognized operator: " + operator.toString()));
                return null;
        }
    }

    private double parseToken(double_val token) {
        if ((token instanceof double_expr)) {
            return ((double_expr) token).execute();
        }
        else if (token instanceof double_token) {
            return ((double_token) token).getVal();
        }
        else {
            id tok = (id) token;
            if (nameTableSingleton.getInstance().getType(tok) != typeIdx.k_Double)
                errorPrinter.throwError(tok.getLineNumber(), new Runtime("Error, attempt to use a non-double ID in a double expression"));
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
