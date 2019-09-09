package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import src.parseTree.tokens.charAt_label;
import src.parseTree.tokens.comma;
import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.start_paren;

public class charAt_expr extends str_expr {
    charAt_label op;
    start_paren startParen;
    str_val strExpr;
    comma sep;
    int_expr intExpr;
    end_paren endParen;

    public charAt_expr(charAt_label op, start_paren startParen, str_val strExpr, comma sep, int_expr intExpr, end_paren endParen) {
        this.op = op;
        this.startParen = startParen;
        this.strExpr = strExpr;
        this.sep = sep;
        this.intExpr = intExpr;
        this.endParen = endParen;
    }

    @Override
    public String execute(String filePath) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
