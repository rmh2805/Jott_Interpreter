package src.parseTree.tokens;

import src.parseTree.tokens.token;

public class end_stmt extends token{
    
    public end_stmt (int lineNum) {
        super(lineNum);
    }

    public String toString () {
        return ";";
    }
}
