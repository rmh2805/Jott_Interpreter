package src.parseTree.tokens;

public class end_paren extends token {
    public end_paren (int lineNumber) {
        super(lineNumber);
    }    

    public String toString () {
        return ")";
    }
}
