package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import src.parseTree.tokens.quote;
import src.parseTree.tokens.str_token;

public class str_literal extends str_expr implements str_val {
    private quote preQuote;
    private str_token data;
    private quote endQuote;

    public str_literal(quote preQuote, str_token data, quote endQuote) {
        if (preQuote == null || data == null || endQuote == null) {
            System.out.println("Error, missing a token to create a string literal");
            System.exit(1);
        }

        this.preQuote = preQuote;
        this.data = data;
        this.endQuote = endQuote;
    }

    public quote getPreQuote() {
        return preQuote;
    }

    public String getVal(String filePath) {
        return data.toString();
    }

    public str_token getData() {
        return data;
    }

    public quote getEndQuote() {
        return endQuote;
    }

    @Override
    public String execute(String filePath) {
        return data.toString();
    }

    public String toString() {
        return preQuote.toString() + data.toString() + endQuote.toString();
    }
}
