package src.parseTree.nodes;

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

    public Integer execute() {
        this.fixChildren();
        // bind function definition to identifier in function table
        nameTableSingleton.getInstance().mapFun(name, this);
        return 0;
    }

    Object call(fc_p_lst values) {
        params.execute(values);
        body.execute();
        if (ret_val instanceof int_return) return ((int_return) ret_val).execute();
        else if (ret_val instanceof double_return) return ((double_return) ret_val).execute();
        else if (ret_val instanceof str_return) return ((str_return) ret_val).execute();
        return null;
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
