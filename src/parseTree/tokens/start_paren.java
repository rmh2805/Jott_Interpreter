package src.parseTree.tokens;

import src.parseTree.tokens.token;

public class start_paren extends token{
    public start_paren (int lineNumber) {
        super(lineNumber); 
    }

    public String toString () {
        return "(";
    }

}
