package src.parseTree.nodes;

public class stmt_lst extends node {
    private stmt statement;
    private stmt_lst next;

    public void fixChildren() {
        if (children.size() == 0) return;
        statement = (stmt) children.get(0);
        if (children.size() >= 2) { // if stmt -> expr,end_stmt stmt_list has three children
            if (children.get(children.size() - 1) instanceof b_stmt_lst)
                next = (stmt_lst) children.get(children.size() - 1);
        }
    }

    public void execute() {
        this.fixChildren();
        if (statement == null) return;
        statement.execute();
        if (next != null) next.execute();
    }

    /**
     * The string representation of this statement list
     *
     * @return the string representation of this statement list
     */
    public String toString() {
        if (statement == null) return "";
        String result = statement.toString() +
                (children.size() > 2 ? ";" : "") + // print end_stmt if exists
                "\n";
        if (next != null) result += next.toString() + "\n";
        return result;
    }
}
