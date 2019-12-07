package src.parseTree.nodes;

public class f_call_param_lst extends node {
    private expr value;
    private f_call_param_lst next;

    public void fixChildren() {
        value = (expr) children.get(0);
        // comma(1)
        if (children.size() == 3) next = (f_call_param_lst) children.get(2);
    }

    expr getFirst() {
        return (expr) children.get(0);
    }

    f_call_param_lst getNext() {
        if (children.size() == 3) return (f_call_param_lst) children.get(2);
        return null;
    }

    public int getLineNumber() {
        return this.getFirst().getLineNumber();
    }

    public int getIndex() {
        return this.getFirst().getIndex();
    }
}
