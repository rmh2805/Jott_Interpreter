package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.parseTree.categories.str_val;
import src.parseTree.tokens.charAt_label;
import src.parseTree.tokens.comma;
import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.start_paren;

public class charAt_expr extends str_expr {
    private charAt_label op;
    private start_paren startParen;
    private str_val strExpr;
    private comma sep;
    private int_expr intExpr;
    private end_paren endParen;

    public charAt_expr(charAt_label op, start_paren startParen, str_val strExpr, comma sep, int_expr intExpr, end_paren endParen) {
        this.op = op;
        this.startParen = startParen;
        this.strExpr = strExpr;
        this.sep = sep;
        this.intExpr = intExpr;
        this.endParen = endParen;
    }

    @Override
    public String execute() {
        String arg1 = str_val.execute(strExpr);
        Integer arg2 = intExpr.execute();

        if (arg2 < 0 || arg2 >= arg1.length())
            errorPrinter.throwError(intExpr.getLineNumber(), new Runtime("Trying to access an index (" + arg2 + ") outside of the provided string"));

        return Character.toString(arg1.charAt(arg2));
    }

    @Override
    public String toString() {
        return op.toString() + startParen.toString() + strExpr.toString() + sep.toString() + intExpr.toString() + endParen.toString();
    }
}
