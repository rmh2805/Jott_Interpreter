package src.parseTree.nodes;

public class for_stmt extends stmt<Integer> {
    private asmt init;
    private int_expr cond;
    private r_asmt inc;
    private b_stmt_lst body;

    public void fixChildren() {
        init = (asmt) children.get(0);
        cond = (int_expr) children.get(1);
        inc = (r_asmt) children.get(2); // todo throw error if inc not related to init
        body = (b_stmt_lst) children.get(3);
    }

    public Integer execute() {
        this.fixChildren();
        init.execute();
        while (cond.execute() == 1) {
            body.execute();
            inc.execute();
        }

        return 0;
    }

    public String toString() {
        return String.format("for (%s %s; %s) {\n%s\n}", init.toString(), cond.toString(), inc.toString());
    }
}
