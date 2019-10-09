package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.parseTree.categories.str_val;
import src.parseTree.tokens.charAt_label;
import src.parseTree.tokens.comma;
import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.start_paren;

import java.util.ArrayList;
import java.util.List;

public class charAt_expr extends str_expr {
    private charAt_label op;
    private start_paren startParen;
    private str_val strExpr;
    private comma sep;
    private int_expr intExpr;
    private end_paren endParen;
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
        if (children.size() >= 6) {
            fixChildren();
        }
    }

    public List<Object> getChildren() {
        return children;
    }

    public void fixChildren() {
        this.charAt_expr_set((charAt_label) children.get(0), (start_paren) children.get(1), (str_val) children.get(2),
                (comma) children.get(3), (int_expr) children.get(4), (end_paren) children.get(5));
    }

    public charAt_expr() {}

    public void charAt_expr_set(charAt_label op, start_paren startParen, str_val strExpr, comma sep, int_expr intExpr, end_paren endParen) {
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

        if (arg2 >= arg1.length())
            errorPrinter.throwError(intExpr.getLineNumber(), new Runtime("Trying to access an index (" + arg2 + ") outside of the provided string"));

        return Character.toString(arg1.charAt(arg2));
    }

    @Override
    public String toString() {
        return op.toString() + startParen.toString() + strExpr.toString() + sep.toString() + intExpr.toString() + endParen.toString();
    }
}
