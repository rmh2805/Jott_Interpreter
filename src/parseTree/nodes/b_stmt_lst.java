package src.parseTree.nodes;

public class b_stmt_lst extends node {
    private b_stmt statement;
    private b_stmt_lst next;

    public void fixChildren() {
        if (children.size() == 0) return;
        statement = (b_stmt) children.get(0);
        if (children.size() >= 2) { // if b_stmt -> expr,end_stmt b_stmt_list has three children
            next = (b_stmt_lst) children.get(children.size() - 1);
        }
    }

    public void execute() {
        this.fixChildren();
        if (statement == null) return;
        statement.execute();
        if (next != null) next.execute();
    }

    /**
     * The string representation of this block statement list
     *
     * @return the string representation of this block statement list
     */
    public String toString() {
        if (statement == null) return "";
        String result = "\t" + statement.toString() +
                (children.size() > 2 ? ";" : ""); // print end_stmt if exists
        if (next != null) result += "\n" + next.toString();
        return result;
    }

}
