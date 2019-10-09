package src.parseTree.nodes;

import src.parseTree.categories.Type;
import src.parseTree.tokens.asmt_op;
import src.parseTree.tokens.end_stmt;
import src.parseTree.tokens.id;

import java.util.ArrayList;
import java.util.List;

public class asmt extends stmt<Integer> implements node {
    private Type t;
    private id name;
    private asmt_op op;
    private expr exp;
    private end_stmt endStmt;

    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        //todo Potentially add validation in there
        t = (Type) children.get(0);
        name = (id) children.get(1);
        op = (asmt_op) children.get(3);
        exp = (expr) children.get(4);
        endStmt = (end_stmt) children.get(5);
    }

    public List<Object> getChildren() {
        return children;
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
