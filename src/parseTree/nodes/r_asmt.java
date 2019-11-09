package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.tokens.*;
import src.typeIdx;

public class r_asmt extends b_stmt<Integer> {
    private id name;
    private asmt_op op;
    private expr exp;

    public void fixChildren() {
        name = (id) children.get(0);
        op = (asmt_op) children.get(1);
        exp = (expr) children.get(2);
    }

    public String getId() {
        return children.get(0).toString();
    }

    @Override
    public Integer execute() {
        this.fixChildren();
        nameTableSingleton nameTable = nameTableSingleton.getInstance();

        //Get the labeled type for the name table singleton and for type validation
        typeIdx t = nameTable.getType(name);
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
        return name.toString() + op.toString() + exp.toString();
    }
}
