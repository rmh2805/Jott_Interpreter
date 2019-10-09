package src.parseTree.tokens;

import src.parseTree.categories.str_val;

public class str_token extends token implements str_val {
    private String data;

    public str_token(int lineNumber, String data) {
        super(lineNumber);
        this.data = data;
    }


    /**
     * Returns the string literal's data
     *
     * @return The string literal's data
     */
    public String toString() {
        return data;
    }
}
