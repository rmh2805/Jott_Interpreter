package src.parseTree.nodes;

public class while_stmt extends stmt<Integer> {
    private int_expr cond;
    private b_stmt_lst body;

    public void fixChildren() {
        // while(0), start_paren(1), end_paren(3)
        cond = (int_expr) children.get(2);
        // start_brace(4), end_brace(6)
        body = (b_stmt_lst) children.get(5);
    }

    public Integer execute() {
        this.fixChildren();
        while (cond.execute() != 0) body.execute();

        return 0;
    }

    public String toString() {
        return String.format("while (%s) {\n%s\n}", cond.toString(), body.toString());
    }
}
