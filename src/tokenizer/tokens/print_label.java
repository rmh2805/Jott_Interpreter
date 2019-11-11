package src.tokenizer.tokens;

public class print_label extends token {

    public print_label(int lineNum) {
        super(lineNum);
    }
    
    public String toString () {
        return "print";
    }
}
