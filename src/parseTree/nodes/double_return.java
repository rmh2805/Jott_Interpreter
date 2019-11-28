package src.parseTree.nodes;

public class double_return extends node {
    private double_expr ret_val;

    public void fixChildren() {
        // return(0)
        ret_val = (double_expr) children.get(1);
        // end_stmt
    }

    public Double execute() {
        this.fixChildren();
        return ret_val.execute();
    }

    public String toString() {
        return String.format("return %s;", ret_val.toString());
    }
}
