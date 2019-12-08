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
        t = (Type) children.get(0);
        name = (id) children.get(1);
        op = (asmt_op) children.get(2);
        exp = (expr) children.get(3);
        endStmt = (end_stmt) children.get(4);
    }

    public typeIdx getType() {
        switch (children.get(0).toString()) {
            case "Integer":
                return typeIdx.k_Integer;
            case "Double":
                return typeIdx.k_Double;
            case "String":
                return typeIdx.k_String;
            default:
                return typeIdx.k_Void;
        }
    }

    public String getId() {
        return children.get(1).toString();
    }

    @Override
    public Integer execute() {
        this.fixChildren();
        nameTableSingleton nameTable = nameTableSingleton.getInstance();

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
            errorPrinter.throwError(name, new Syntax("Unhandled 'type' in assignment. Implementer error. We suck."));
        }

        switch (t) {
            case k_Double:
                if (exp instanceof double_expr)
                    nameTable.setDouble(name, ((double_expr) exp).execute());
                break;
            case k_Integer:
                if (exp instanceof int_expr)
                    nameTable.setInt(name, ((int_expr) exp).execute());
                break;
            case k_String:
                if (exp instanceof str_expr)
                    nameTable.setString(name, ((str_expr) exp).execute());
                break;
            default:
                errorPrinter.throwError(op, new Runtime("Attempt to assign to incompatible type"));
        }

        return 0;
    }

    @Override
    public String toString() {
        return t.toString() + name.toString() + op.toString() + exp.toString() + endStmt.toString();
    }
}
