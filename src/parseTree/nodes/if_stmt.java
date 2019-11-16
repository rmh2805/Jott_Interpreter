package src.parseTree.nodes;

public class if_stmt extends b_stmt<Integer> {
    private expr cond;
    private b_stmt_lst body;
    private b_stmt_lst alt;

    public void fixChildren() {
        // if(0), start_paren(1), end_paren(3)
        cond = (expr) children.get(2);
        // start_brace(4), end_brace(6)
        body = (b_stmt_lst) children.get(5);
        // else(7), start_brace(8), end_brace(10)
        if (children.size() > 7) alt = (b_stmt_lst) children.get(9);
    }

    public Integer execute() {
        this.fixChildren();
        Object condValue = cond.execute();
        if (condValue instanceof String ||
                condValue instanceof Integer && ((int) condValue) != 0 ||
                condValue instanceof Double && ((double) condValue) != 0.0)
            body.execute();
        else {
            if (alt != null) alt.execute();
        }

        return 0;
    }

    public String toString() {
        return String.format("if (%s) {\n%s\n}", cond.toString(), body.toString()) +
                ((alt != null) ? String.format("else {\n%s\n}", alt.toString()) : "");
    }
}
