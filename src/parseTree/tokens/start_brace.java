package src.parseTree.tokens;

public class start_brace extends token{
    public start_brace (int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString () {
        return "{";
    }

}
