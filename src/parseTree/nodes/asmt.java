package src.parseTree.nodes;

import src.parseTree.categories.type;
import src.parseTree.tokens.asmt_op;
import src.parseTree.tokens.end_stmt;
import src.parseTree.tokens.id;

public class asmt extends stmt<Integer> {
    private type t;
    private id name;
    private asmt_op op;
    private expr exp;
    private end_stmt endStmt;

    public void fixChildren() {
        //todo Potentially add validation in there
        t = (type) children.get(0);
        name = (id) children.get(1);
        op = (asmt_op) children.get(3);
        exp = (expr) children.get(4);
        endStmt = (end_stmt) children.get(5);
    }

    public String getType() {
        return children.get(0).toString();
    }

    public String getId() {
        return children.get(1).toString();
    }

    @Override
    public Integer execute() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
