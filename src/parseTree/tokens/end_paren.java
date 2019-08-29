package src.parseTree.tokens;

import src.parseTree.tokens.token;

public class end_paren extends token {
    public end_paren (int lineNumber) {
        super(lineNumber);
    }    

    public String toString () {
        return ")";
    }
}
