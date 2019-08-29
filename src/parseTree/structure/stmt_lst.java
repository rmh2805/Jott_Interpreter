package src.parseTree.structure;

import src.parseTree.nodes.stmt;

public class stmt_lst {
    private stmt statement;
    private stmt_lst next;

    public stmt_lst (stmt statement) {
        this.statement = statement;
        this.next = null;
    }

    public void setNextStatement(stmt_lst next) {
        this.next = next;
    }

    public stmt getStatement () {
        return this.statement;
    }

    public stmt_lst getNext () {
        return this.next;
    }

}
