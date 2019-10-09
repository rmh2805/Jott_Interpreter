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

import java.util.ArrayList;
import java.util.List;

public class int_expr extends expr<Integer> implements int_val, node {
    private int_val lVal;
    private op operator;
    private int_val rVal;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
        if (children.size() >= 1) {
            fixChildren();
        }
    }

    public void fixChildren() {
        if (children.size() == 1) {
            this.int_expr_set((int_val) children.get(0), null, null);
        } else if (children.size() > 2) {
            this.int_expr_set((int_val) children.get(0), (op) children.get(1), (int_val) children.get(2));
        }
    }

    public List<Object> getChildren() {
        return children;
    }

    public int_expr() {}

    public void int_expr_set(int_val lVal, op operator, int_val rVal) {
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

    public int getLineNumber() {
        if(lVal instanceof int_expr)
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
                errorPrinter.throwError(((id) token).getLineNumber(), new Runtime("Error, attempt to use a non-int ID in a double expression"));
            return nameTableSingleton.getInstance().getInt(tok);
        }

    }

    @Override
    public String toString() {
        if (operator == null && rVal == null){
            return lVal.toString();
        } else {
            return lVal.toString() + operator.toString() + rVal.toString();
        }
    }
}
