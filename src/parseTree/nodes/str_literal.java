package src.parseTree.nodes;

import src.parseTree.nodes.value_types.str_val;
import src.parseTree.nodes.value_types.value;
import src.parseTree.tokens.quote;
import src.parseTree.tokens.str_token;

public class str_literal implements str_val, value<String> {
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

    public String getValue() {
        return data.toString();
    }

    public str_token getData() {
        return data;
    }

    public quote getEndQuote() {
        return endQuote;
    }

    public String toString() {
        return preQuote.toString() + data.toString() + endQuote.toString();
    }
}
