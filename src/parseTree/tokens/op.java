package src.parseTree.tokens;

import src.parseTree.tokens.token;

public class op extends token{
    private String operator;

    public op (int lineNum, String operator) {
        super(lineNum);
        this.operator = operator;
    }

    public op (int lineNum, char operator) {
        this(lineNum, ""+operator);
    }
    
    public String toString () {
        return this.operator;
    }
}
