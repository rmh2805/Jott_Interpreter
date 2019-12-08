package src.parseTree.nodes;

import src.dataFrame;
import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.nameTableSingleton;
import src.parseTree.categories.Type;
import src.parseTree.tokens.id;
import src.typeIdx;

public class f_defn extends stmt<Integer> {
    private Type t;
    private id name;
    private p_lst params;
    private f_stmt_lst body;
    private node ret_val;

    public void fixChildren() {
        t = (Type) children.get(0);
        name = (id) children.get(1);
        // start_paren(2)
        if (children.get(3) instanceof p_lst) params = (p_lst) children.get(3);
        // end_paren, start_brace
        if (this.getType() == typeIdx.k_Void) {
            body = (f_stmt_lst) children.get(children.size() - 2);
        } else {
            body = (f_stmt_lst) children.get(children.size() - 3);
            ret_val = (node) children.get(children.size() - 2);
        }
        // end_brace
    }

    public String getId() {
        return children.get(1).toString();
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

    public p_lst getParams() {
        if (children.get(3) instanceof p_lst) return (p_lst) children.get(3);
        return null;
    }

    public Integer execute() {
        this.fixChildren();
        // bind function definition to identifier in function table
        nameTableSingleton.getInstance().mapFun(name, this);
        return 0;
    }

    Object call(fc_p_lst values) {
        Object result = null;
        nameTableSingleton nT = nameTableSingleton.getInstance();
        dataFrame dF = new dataFrame();
        if (params == null && values != null)
            errorPrinter.throwError(values.getLineNumber(), values.getIndex(), new Syntax("Too many arguments"));
        else if (params != null && values != null) params.execute(values, dF);
        nT.addStack(dF);
        body.execute();
        if (ret_val instanceof int_return) result = ((int_return) ret_val).execute();
        else if (ret_val instanceof double_return) result = ((double_return) ret_val).execute();
        else if (ret_val instanceof str_return) result = ((str_return) ret_val).execute();
        nT.popStack();
        return result;
    }

    /**
     * The string representation of this function definition
     *
     * @return the string representation of this function definition
     */
    public String toString() {
        return String.format("%s %s(%s) {\n%s%s\n}", t.toString(), name.toString(),
                (params == null) ? "" : params.toString(),
                body.toString(),
                (ret_val == null) ? "" : ("\n" + ret_val.toString()));
    }

}
