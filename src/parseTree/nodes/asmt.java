package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.errorHandling.types.Syntax;
import src.nameTableSingleton;
import src.parseTree.categories.Type;
import src.parseTree.tokens.*;
import src.typeIdx;

import java.util.ArrayList;
import java.util.List;

public class asmt extends stmt<Integer> {
    private Type t;
    private id name;
    private asmt_op op;
    private expr exp;
    private end_stmt endStmt;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
        if (children.size() >= 5) {
            fixChildren();
        }
    }

    public List<Object> getChildren() {
        return children;
    }

    public void fixChildren() {
        this.asmt_set((Type) children.get(0), (id) children.get(1), (asmt_op) children.get(2),
                (expr) children.get(3), (end_stmt) children.get(4));
    }

    public asmt() {}

    public void asmt_set(Type t, id name, asmt_op op, expr exp, end_stmt endStmt) {
        this.t = t;
        this.name = name;
        this.op = op;
        this.exp = exp;
        this.endStmt = endStmt;
    }

    @Override
    public Integer execute() {
        if (nameTableSingleton.getInstance().isAssigned(name)) {
            errorPrinter.throwError(name.getLineNumber(), new Runtime("Attempting to assign to an already assigned id"));
        }

        //Get the labeled type for the name table singleton and for type validation
        typeIdx t;
        if (this.t instanceof int_label)
            t = typeIdx.k_Integer;
        else if (this.t instanceof double_label)
            t = typeIdx.k_Double;
        else if (this.t instanceof str_label)
            t = typeIdx.k_String;
        else {
            t = typeIdx.k_Integer;
            errorPrinter.throwError(name.getLineNumber(), new Syntax("Unhandled 'type' in assignment. Implementer error. We suck."));
        }

        switch (t) {
            case k_Double:
                if (exp instanceof double_expr)
                    nameTableSingleton.getInstance().setDouble(name, ((double_expr) exp).execute());
                break;
            case k_Integer:
                if (exp instanceof int_expr)
                    nameTableSingleton.getInstance().setInt(name, ((int_expr) exp).execute());
                break;
            case k_String:
                if (exp instanceof str_expr)
                    nameTableSingleton.getInstance().setString(name, ((str_expr) exp).execute());
                break;
            default:
                errorPrinter.throwError(op.getLineNumber(), new Runtime("Attempted to cast incompatible types"));
        }

        return 0;
    }

    public String getType() {
        return children.get(0).toString();
    }

    public String getId() {
        return children.get(1).toString();
    }

    @Override
    public String toString() {
        return t.toString() + " " + name.toString() + " " + op.toString() + " " + exp.toString() +  endStmt.toString();
    }
}
