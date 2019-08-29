package src.parseTree.structure;

import src.parseTree.structure.stmt_lst;

public class program {
    private stmt_lst firstStatement;

    public program () {
        firstStatement = null;
    }

    public void setStatement (stmt_lst statement) {
        this.firstStatement = statement;
    }

    public stmt_lst getStatement () {
        return this.firstStatement;
    }

}
