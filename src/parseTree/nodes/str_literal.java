package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.str_val;
import src.parseTree.tokens.id;
import src.parseTree.tokens.str_token;
import src.typeIdx;

public class str_literal extends str_expr implements str_val {
    private str_token data;

    public void fixChildren() {
        if (children.get(0) instanceof str_token) {
            data = ((str_token) children.get(0));
        }
        else if (children.get(0) instanceof id) {
            id tok = (id) children.get(0);
            if (nameTableSingleton.getInstance().getType(tok) != typeIdx.k_String)
                errorPrinter.throwError(tok, new Runtime("Error, attempt to use a non-String ID in a String expression"));
            String val = nameTableSingleton.getInstance().getString(tok);
            data = new str_token(tok.getLineNumber(), tok.getIndex(), val);
        }
    }

    public String execute() {
        this.fixChildren();
        return data.toString();
    }

    public int getLineNumber() {
        return data.getLineNumber();
    }

    public int getIndex() {
        return data.getIndex();
    }

    public String toString() {
        return data.toString();
    }
}
