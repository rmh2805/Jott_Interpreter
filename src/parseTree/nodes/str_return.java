package src.parseTree.nodes;

public class str_return extends node {
    private str_expr ret_val;

    public void fixChildren() {
        // return(0)
        ret_val = (str_expr) children.get(1);
        // end_stmt
    }

    public String execute() {
        this.fixChildren();
        return ret_val.execute();
    }

    public String toString() {
        return String.format("return %s;", ret_val.toString());
    }
}
