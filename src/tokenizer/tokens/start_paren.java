package src.tokenizer.tokens;

public class start_paren extends token{
    public start_paren (int lineNumber) {
        super(lineNumber); 
    }

    public String toString () {
        return "(";
    }

}
