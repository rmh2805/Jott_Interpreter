package src.parseTree.tokens;

public class print_label extends token {

    public print_label(int lineNumber, int index) {
        super(lineNumber, index);
    }
    
    public String toString () {
        return "print";
    }
}
