package src.parseTree.nodes;

public class fc_p_lst extends node {
    private expr value;
    private fc_p_lst next;

    public void fixChildren() {
        value = (expr) children.get(0);
        // comma(1)
        if (children.size() == 3) next = (fc_p_lst) children.get(2);
    }

    expr getFirst() {
        return (expr) children.get(0);
    }

    fc_p_lst getNext() {
        if (children.size() == 3) return (fc_p_lst) children.get(2);
        return null;
    }

    public int getLineNumber() {
        return this.getFirst().getLineNumber();
    }

    public int getIndex() {
        return this.getFirst().getIndex();
    }
}
