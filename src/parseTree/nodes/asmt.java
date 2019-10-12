package src.parseTree.nodes;


import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.errorHandling.types.Syntax;
import src.nameTableSingleton;
import src.parseTree.categories.Type;
import src.parseTree.tokens.*;
import src.typeIdx;

public class asmt extends stmt<Integer> {
    private Type t;
    private id name;
    private asmt_op op;
    private expr exp;
    private end_stmt endStmt;

    public void fixChildren() {
        //todo Potentially add validation in there
        t = (Type) children.get(0);
        name = (id) children.get(1);
        op = (asmt_op) children.get(2);
        exp = (expr) children.get(3);
        endStmt = (end_stmt) children.get(4);
    }

    public String getType() {
        return children.get(0).toString();
    }

    public String getId() {
        return children.get(1).toString();
    }

    @Override
    public Integer execute() {
        this.fixChildren();
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
                errorPrinter.throwError(op.getLineNumber(), new Runtime("Attempt to assign to incompatible type"));
        }

        return 0;
    }

    @Override
    public String toString() {
        return t.toString() + name.toString() + op.toString() + exp.toString() + endStmt.toString();
    }
}
