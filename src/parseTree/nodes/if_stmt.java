package src.parseTree.nodes;

public class if_stmt extends stmt<Integer> {
    private int_expr cond; // todo writeup states expr
    private b_stmt_lst body;
    private b_stmt_lst alt;

    public void fixChildren() {
        cond = (int_expr) children.get(0); // todo writeup states expr
        body = (b_stmt_lst) children.get(1);
        if (children.size() == 3) alt = (b_stmt_lst) children.get(2);
    }

    public Integer execute() {
        this.fixChildren();
        if (cond.execute() == 1) body.execute(); // todo writeup states expr
        else alt.execute();

        return 0;
    }

    public String toString() {
        return String.format("if (%s) {\n%s\n}", cond.toString(), body.toString()) +
                ((alt != null) ? String.format("else {\n%s\n}", alt.toString()) : "");
    }
}
