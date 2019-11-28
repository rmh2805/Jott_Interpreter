package src.parseTree.nodes;

public class f_stmt_lst extends node {
    private stmt statement;
    private f_stmt_lst next;

    public void fixChildren() {
        if (children.size() == 0) return;
        statement = (stmt) children.get(0);
        if (children.size() >= 2) { // if f_stmt_lst -> expr,end_stmt f_stmt_lst has three children
            if (children.get(children.size() - 1) instanceof f_stmt_lst)
                next = (f_stmt_lst) children.get(children.size() - 1);
        }
    }

    public void execute() {
        this.fixChildren();
        if (statement != null) statement.execute();
        if (next != null) next.execute();
    }

    /**
     * The string representation of this function body
     *
     * @return the string representation of this function body
     */
    public String toString() {
        if (statement == null) return "";
        String result = "\t" + statement.toString() +
                (children.size() > 2 ? ";" : ""); // print end_stmt if exists
        if (next != null) result += "\n" + next.toString();
        return result;
    }

}
