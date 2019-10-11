package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.str_val;
import src.parseTree.tokens.id;
import src.parseTree.tokens.quote;
import src.parseTree.tokens.str_token;
import src.typeIdx;

import java.util.ArrayList;
import java.util.List;

public class str_literal extends str_expr implements str_val, node {
    private quote preQuote;
    private str_token data;
    private quote endQuote;
    private id alias;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        //todo Assign the proper children to their fields
        if (children.get(0) instanceof str_token) {
            data = ((str_token) children.get(0));
            this.str_literal_set(new quote(data.getLineNumber()), data, new quote(data.getLineNumber()), null);
        }
        else {
            this.str_literal_set(null, null, null, (id) children.get(0));
        }
    }

    public List<Object> getChildren() {
        return children;
    }

    public str_literal() {
    }

    public void str_literal_set(quote preQuote, str_token data, quote endQuote, id alias) {
        if (preQuote == null && data == null && endQuote == null && alias == null) {
            System.out.println("Error, missing a token to create a string literal");
            System.exit(1);
        }

        this.preQuote = preQuote;
        this.data = data;
        this.endQuote = endQuote;
        this.alias = alias;
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
        if(data != null) {
            return data.toString();
        }
        else {
            if(!(nameTableSingleton.getInstance().isAssigned(alias) &&
                    nameTableSingleton.getInstance().getType(alias) == typeIdx.k_String)) {
                //Either the provided ID isn't assigned or it isn't a string
                errorPrinter.throwError(alias.getLineNumber(), new Runtime("Unassigned or mistyped variable, expected a String"));
            }

            return nameTableSingleton.getInstance().getString(alias);
        }
    }

    public String toString() {
        return preQuote.toString() + data.toString() + endQuote.toString();
    }
}
