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

    @Override
    public Integer execute() {
        this.fixChildren();
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
                if (right == 0) {
                    errorPrinter.throwError(operator, new Runtime("Divide by Zero"));
                }
                return left / right;
            case "^":
                return (int) java.lang.Math.pow(left, right);
            default:
                errorPrinter.throwError(operator, new Runtime("Unrecognized operator: " + operator.toString()));
                return null;
        }
    }

    public int getLineNumber() {
        if (lVal instanceof token) return ((token) lVal).getLineNumber();
        else return ((int_expr) lVal).getLineNumber();
    }

    public int getIndex() {
        if (lVal instanceof token) return ((token) lVal).getIndex();
        else return ((int_expr) lVal).getIndex();
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
                errorPrinter.throwError(tok, new Runtime("Error, attempt to use a non-int ID in an int expression"));
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
