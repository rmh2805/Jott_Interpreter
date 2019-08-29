package src.parseTree.tokens;

import src.parseTree.tokens.token;

public class id extends token{
    private String id;

    public id (int lineNum, String id) {
        super(lineNum);
        this.id = id;
    }
    
    public String toString () {
        return id;
    }
}
