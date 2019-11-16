package src.parseTree.nodes;

public class if_stmt extends stmt<Integer> {
    private int_expr cond; // todo writeup states expr
    private b_stmt_lst body;
    private b_stmt_lst alt1;
    private if_stmt alt2;

    public void fixChildren() {
        // if(0), start_paren(1), end_paren(3)
        cond = (int_expr) children.get(2); // todo writeup states expr
        // start_brace(4), end_brace(6)
        body = (b_stmt_lst) children.get(5);
        // else(7), start_brace(8), end_brace(10)
        if (children.size() > 7) {
            if (children.get(9) instanceof if_stmt) {
                alt2 = (if_stmt) children.get(9);
            }
            else {
                alt1 = (b_stmt_lst) children.get(9);
            }
        }
    }

    public Integer execute() {
        this.fixChildren();
        if (cond.execute() == 1) body.execute(); // todo writeup states expr
        else {
            if (alt1 != null) {
                alt1.execute();
            }
            else {
                alt2.execute();
            }
        }

        return 0;
    }

    public String toString() {
        return("if");
        //return String.format("if (%s) {\n%s\n}", cond.toString(), body.toString()) +
                //((alt != null) ? String.format("else {\n%s\n}", alt.toString()) : "");
    }
}
