package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.double_val;
import src.parseTree.categories.int_val;
import src.parseTree.categories.str_val;
import src.parseTree.tokens.*;
import src.typeIdx;

public class int_expr extends expr<Integer> implements int_val {
    private Object lVal;
    private token operator;
    private Object rVal;
    private typeIdx type;

    public void fixChildren() {
        lVal = children.get(0);
        nameTableSingleton nT = nameTableSingleton.getInstance();
        if (lVal instanceof id) type = nT.getType((id) lVal);
        else if (lVal instanceof f_call) type = nT.getFunType(((f_call) lVal).getName());
        else if (lVal instanceof int_val) type = typeIdx.k_Integer;
        else if (lVal instanceof double_val) type = typeIdx.k_Double;
        else if (lVal instanceof str_val) type = typeIdx.k_String;

        if (children.size() == 2) {
            System.out.println("Error, int expression creation must provide either only lVal or lVal, operator, and rVal");
            System.exit(1);
        }
        if (children.size() == 3) {
            operator = (token) children.get(1);
            rVal = children.get(2);
        }
    }

    public int_expr() {}

    @Override
    public Integer execute() {
        this.fixChildren();
        
        double left = 0;
        String strLeft = "";
        if (type == typeIdx.k_Integer) {
            if (lVal instanceof f_call) left = (Integer) ((f_call) lVal).execute();
            else left = parseToken((int_val) lVal);
        }
        else if (type == typeIdx.k_Double) {
            if (lVal instanceof f_call) left = (Double) ((f_call) lVal).execute();
            else left = double_val.execute((double_val) lVal);
        }
        else if (type == typeIdx.k_String) {
            if (lVal instanceof f_call) strLeft = (String) ((f_call) lVal).execute();
            else strLeft = str_val.execute((str_val) lVal);
        }

        if (operator == null)
            return (int) left;

        // todo this does not work for ids
        double right = 0;
        String strRight = "";
        if (type == typeIdx.k_Integer) {
            if (rVal instanceof f_call) right = (Integer) ((f_call) rVal).execute();
            else right = parseToken((int_val) rVal);
        }
        else if (type == typeIdx.k_Double) {
            if (rVal instanceof f_call) right = (Double) ((f_call) rVal).execute();
            else right = double_val.execute((double_val) rVal);
        }
        else if (type == typeIdx.k_String) {
            if (rVal instanceof f_call) strRight = (String) ((f_call) rVal).execute();
            else strRight = str_val.execute((str_val) rVal);
        }

        switch (operator.toString()) {
            case "+":
                return (int) (left + right);
            case "-":
                return (int) (left - right);
            case "*":
                return (int) (left * right);
            case "/":
                if (right == 0) {
                    errorPrinter.throwError(operator, new Runtime("Divide by Zero"));
                }
                return (int) (left / right);
            case "^":
                return (int) java.lang.Math.pow(left, right);
            case ">":
                if (type == typeIdx.k_String) return strLeft.compareTo(strRight) > 0 ? 1 : 0;
                return left > right ? 1 : 0;
            case ">=":
                if (type == typeIdx.k_String) return strLeft.compareTo(strRight) >= 0 ? 1 : 0;
                return left >= right ? 1 : 0;
            case "<":
                if (type == typeIdx.k_String) return strLeft.compareTo(strRight) < 0 ? 1 : 0;
                return left < right ? 1 : 0;
            case "<=":
                if (type == typeIdx.k_String) return strLeft.compareTo(strRight) <= 0 ? 1 : 0;
                return left <= right ? 1 : 0;
            case "==":
                if (type == typeIdx.k_String) return strLeft.compareTo(strRight) == 0 ? 1 : 0;
                return left == right ? 1 : 0;
            case "!=":
                if (type == typeIdx.k_String) return strLeft.compareTo(strRight) != 0 ? 1 : 0;
                return left != right ? 1 : 0;
            default:
                errorPrinter.throwError(operator, new Runtime("Unrecognized operator: " + operator.toString()));
                return null;
        }
    }

    public int getLineNumber() {
        if (lVal instanceof token) return ((token) lVal).getLineNumber();
        else if (type == typeIdx.k_Integer) return ((int_expr) lVal).getLineNumber();
        else if (type == typeIdx.k_Double) return ((double_expr) lVal).getLineNumber();
        else return ((str_expr) lVal).getLineNumber();
    }

    public int getIndex() {
        if (lVal instanceof token) return ((token) lVal).getIndex();
        else if (type == typeIdx.k_Integer) return ((int_expr) lVal).getIndex();
        else if (type == typeIdx.k_Double) return ((double_expr) lVal).getIndex();
        else return ((str_expr) lVal).getIndex();
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
