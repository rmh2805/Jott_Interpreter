package src;

public class token {
    private String text;
    private int lineNumber;
    
    public token (String text, int lineNumber) {
        this.text = text;
        this.lineNumber = lineNumber;
    }
    
    public String getText () {
        return this.text;
    }
    
    public int getLineNumber () {
        return this.lineNumber;
    }
    
    public String toString () {
        return this.text;
    }
}