package src.parseTree.tokens;

public class end_paren extends token {
    public end_paren (int lineNumber, int index) {
        super(lineNumber, index);
    }    

    public String toString () {
        return ")";
    }
}
