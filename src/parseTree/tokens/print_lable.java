package src.parseTree.tokens;

import src.parseTree.tokens.token;

public class print_lable extends token{
    
    public print_lable (int lineNum) {
        super(lineNum);
    }
    
    public String toString () {
        return "print";
    }
}
