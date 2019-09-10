package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.parseTree.categories.int_val;
import src.parseTree.tokens.end_stmt;
import src.parseTree.tokens.int_token;
import src.parseTree.tokens.op;
import src.parseTree.tokens.id;

public class int_expr extends expr<Integer> implements int_val {
    private int_val lVal;
    private op operator;
    private int_val rVal;

    public int_expr(int_val lVal, op operator, int_val rVal) {
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
        int left = parseToken(lVal);

        if (operator == null)
            return left;

        int right = parseToken(rVal);

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
                errorPrinter.throwError(operator.getLineNumber(), new Runtime("Unrecognized operator: " + operator.toString()));
                return null;
        }
    }

    private int parseToken(int_val token) {
        if ((token instanceof int_expr)) {
            return ((int_expr) token).getValue();
        }
        else if (token instanceof int_token) {
            return ((int_token) token).execute();
        }
        else {
            variable<Integer> var = new variable<>((id) token, Integer.class);
            return var.getValue();
        }

    }

    @Override
    public String toString() {
        return lVal.toString() + operator.toString() + rVal.toString();
    }
}
