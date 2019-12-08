package src.parseTree.nodes;

public class int_return extends node {
    private int_expr ret_val;

    public void fixChildren() {
        // return(0)
        ret_val = (int_expr) children.get(1);
        // end_stmt
    }

    public Integer execute() {
        this.fixChildren();
        return ret_val.execute();
    }

    public String toString() {
        return String.format("return %s;", ret_val.toString());
    }
}
