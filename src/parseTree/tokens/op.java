package src.parseTree.tokens;

public class op extends token{
    private String operator;

    public op (int lineNumber, int index, String operator) {
        super(lineNumber, index);
        this.operator = operator;
    }

    public op (int lineNumber, int index, char operator) {
        this(lineNumber, index, ""+operator);
    }
    
    public String toString () {
        return this.operator;
    }
}
