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

    public asmt(type t, id name, asmt_op op, expr exp, end_stmt endStmt) {
        this.t = t;
        this.name = name;
        this.op = op;
        this.exp = exp;
        this.endStmt = endStmt;
    }

    @Override
    public Integer execute(String filePath) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
