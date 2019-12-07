package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.nameTableSingleton;
import src.parseTree.categories.*;
import src.parseTree.tokens.id;

/**
 * Does what it says on the tin, handling function calls
 */
public class f_call extends b_stmt<Object> implements int_val, double_val, str_val {
    private id name;
    private f_call_param_lst values;

    public void fixChildren() {
        name = (id) children.get(0);
        // start_paren(1)
        if (children.get(2) instanceof f_call_param_lst) values = (f_call_param_lst) children.get(2);
        // end_paren, end_stmt
    }

    public id getName() {
        return (id) children.get(0);
    }

    public Object execute() {
        this.fixChildren();

        f_defn fun = nameTableSingleton.getInstance().getFun(name);
        if (fun.getParams() != null && values == null)
            errorPrinter.throwError(name, new Syntax("Missing arguments"));
        return fun.call(values);
    }

    /**
     * The string representation of this function call
     *
     * @return the string representation of this function call
     */
    public String toString() {
        return String.format("%s(%s);", name.toString(),
                (values == null) ? "" : values.toString());
    }
}
