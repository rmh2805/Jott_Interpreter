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

    public concat_expr(concat_label op, start_paren startParen, str_val lStr, comma sep, str_val rStr, end_paren endParen) {
        this.op = op;
        this.startParen = startParen;
        this.lStr = lStr;
        this.sep = sep;
        this.rStr = rStr;
        this.endParen = endParen;
    }

    @Override
    public String execute() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
