package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import src.parseTree.tokens.quote;
import src.parseTree.tokens.str_token;

import java.util.ArrayList;
import java.util.List;

public class str_literal extends str_expr implements str_val, node {
    private quote preQuote;
    private str_token data;
    private quote endQuote;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public str_literal() {}

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

    public String execute() {
        return data.toString();
    }

    public String toString() {
        return preQuote.toString() + data.toString() + endQuote.toString();
    }
}
