package src.parseTree.nodes;

public class while_stmt extends stmt<Integer> {
    private int_expr cond;
    private b_stmt_lst body;

    public void fixChildren() {
        cond = (int_expr) children.get(0);
        body = (b_stmt_lst) children.get(1);
    }

    public Integer execute() {
        this.fixChildren();
        while (cond.execute() == 1) body.execute();

        return 0;
    }

    public String toString() {
        return String.format("while (%s) {\n%s\n}", cond.toString(), body.toString());
    }
}
