package src.parseTree.nodes;

import src.nameTableSingleton;
import src.parseTree.categories.*;
import src.parseTree.tokens.id;

public class f_call extends b_stmt<Object> implements int_val, double_val, str_val {
    private id name;
    private fc_p_lst values;

    public void fixChildren() {
        name = (id) children.get(0);
        // start_paren(1)
        if (children.get(2) instanceof fc_p_lst) values = (fc_p_lst) children.get(2);
        // end_paren, end_stmt
    }

    public Object execute() {
        this.fixChildren();

        nameTableSingleton nT = nameTableSingleton.getInstance();
        nT.addStack();
        f_defn fun = nT.getFun(name);
        Object result = fun.call(values);
        nT.popStack();
        return result;
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
