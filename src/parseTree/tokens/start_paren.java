package src.parseTree.tokens;

public class start_paren extends token{
    public start_paren (int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString () {
        return "(";
    }

}
