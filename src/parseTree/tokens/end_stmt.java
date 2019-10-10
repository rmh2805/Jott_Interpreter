package src.parseTree.tokens;

public class end_stmt extends token{
    
    public end_stmt (int lineNumber, int index) {
        super(lineNumber, index);
    }

    public String toString () {
        return ";";
    }
}
