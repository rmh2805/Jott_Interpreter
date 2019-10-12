package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import src.parseTree.tokens.comma;
import src.parseTree.tokens.concat_label;
import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.start_paren;

public class concat_expr extends str_expr implements str_val {
    private concat_label op;
    private start_paren startParen;
    private str_val lStr;
    private comma sep;
    private str_val rStr;
    private end_paren endParen;

    public void fixChildren() {
        //todo Add validation here
        op = (concat_label) children.get(0);
        startParen = (start_paren) children.get(1);
        lStr = (str_val) children.get(2);
        sep = (comma) children.get(3);
        rStr = (str_val) children.get(4);
        endParen = (end_paren) children.get(5);
    }

    @Override
    public String execute() {
        this.fixChildren();
        return str_val.execute(lStr).concat(str_val.execute(rStr));
    }

    @Override
    public String toString() {
        return op.toString() + startParen.toString() + lStr.toString() + sep.toString() + rStr.toString() + endParen.toString();
    }
}
