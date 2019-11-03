package src.parseTree.nodes;

public class stmt_lst extends node {
    private stmt statement;
    private stmt_lst next;

    public void fixChildren() {
        statement = (stmt) children.get(0);
        if (children.size() >= 2) { // if stmt -> expr,end_stmt stmt_list has three children
            next = (stmt_lst) children.get(children.size() - 1);
        }
    }

    public void execute() {
        this.fixChildren();
        statement.execute();
        if (next != null) next.execute();
    }

    /**
     * The string representation of this statement list
     *
     * @return the string representation of this statement list
     */
    public String toString() {
        String result = statement.toString() +
                (children.size() > 2 ? ";" : "") + // print end_stmt if exists
                "\n";
        if (next != null) result += next.toString() + "\n";
        return result;
    }
}
