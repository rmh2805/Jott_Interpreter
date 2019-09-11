package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.double_val;
import src.parseTree.tokens.double_token;
import src.parseTree.tokens.id;
import src.parseTree.tokens.op;
import src.typeIdx;

public class double_expr extends expr<Double> implements double_val {
    private double_val lVal;
    private op operator;
    private double_val rVal;


    public double_expr(double_val lVal, op operator, double_val rVal) {
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
            //TODO come back to this
            id tok = (id) token;
            if (nameTableSingleton.getInstance().getType(tok) != typeIdx.k_Double)
                errorPrinter.throwError(((id) token).getLineNumber(), new Runtime("Error, attempt to use a non-double ID in a double expression"));
            return nameTableSingleton.getInstance().getDouble(tok);
        }

    }

    @Override
    public String toString() {
        return lVal.toString() + operator.toString() + rVal.toString();
    }
}
