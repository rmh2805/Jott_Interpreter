package src.parseTree.nodes;

public class program extends node {
    private stmt_lst firstStatement;

    public void fixChildren() {
        if (children.size() == 1)
        firstStatement = (stmt_lst) children.get(0);
    }

    public void execute () {
        this.fixChildren();
        if (firstStatement != null) firstStatement.execute();
    }

    public String toString() {
        String result = "";
        if (firstStatement != null) result = firstStatement.toString();
        return result;
    }

}
