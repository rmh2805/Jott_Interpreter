package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.double_val;
import src.parseTree.tokens.double_token;
import src.parseTree.tokens.id;
import src.parseTree.tokens.op;
import src.typeIdx;

import java.util.ArrayList;
import java.util.List;

public class double_expr extends expr<Double> implements double_val, node {
    private double_val lVal;
    private op operator;
    private double_val rVal;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        lVal = (double_val) children.get(0);
        if (lVal instanceof node)
            ((node) lVal).fixChildren();

        if (children.size() == 2) {
            System.out.println("Error, double expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }
        if (children.size() == 3) {
            operator = (op) children.get(1);
            rVal = (double_val) children.get(2);
            if (rVal instanceof node)
                ((node) rVal).fixChildren();

        }
    }

    public List<Object> getChildren() {
        return children;
    }

    public double_expr() {
    }

    public void double_expr_set(double_val lVal, op operator, double_val rVal) {
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
        double right = 0;

        if (operator == null)
            return left;

        if (rVal instanceof double_expr && ((double_expr) rVal).children.size() == 3) {
            double_expr doubleExpr = new double_expr();
            doubleExpr.double_expr_set(lVal, operator, ((double_val) (((double_expr) rVal).getChildren().get(0))));
            left = doubleExpr.execute();
            double_val tester= ((double_expr) ((double_expr) rVal).getChildren().get(2)).lVal;
            right = parseToken(tester);
            operator = (op) ((double_expr) rVal).getChildren().get(1);
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
                errorPrinter.throwError(((id) token).getLineNumber(), new Runtime("Error, attempt to use a non-double ID in a double expression"));
            return nameTableSingleton.getInstance().getDouble(tok);
        }

    }

    @Override
    public String toString() {
        return lVal.toString() + operator.toString() + rVal.toString();
    }
}
