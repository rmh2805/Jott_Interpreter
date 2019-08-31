package src.parseTree.nodes;

import src.parseTree.tokens.quote;
import src.parseTree.tokens.str_token;

public class str_literal {
    private quote preQuote;
    private str_token data;
    private quote endQuote;

    public str_literal(quote preQuote, str_token data, quote endQuote) {
        this.preQuote = preQuote;
        this.data = data;
        this.endQuote = endQuote;
    }
}
