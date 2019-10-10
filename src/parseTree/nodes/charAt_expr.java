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

public class charAt_expr extends str_expr implements node {
    private charAt_label op;
    private start_paren startParen;
    private str_val strExpr;
    private comma sep;
    private int_expr intExpr;
    private end_paren endParen;
    
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        //todo Assign the proper children to their fields
        op = (charAt_label) children.get(0);
        startParen = (start_paren) children.get(1);
        strExpr = (str_expr) children.get(2);
        sep = (comma) children.get(3);
        intExpr = (int_expr) children.get(4);
        endParen = (end_paren) children.get(5);
    }

    public List<Object> getChildren() {
        return children;
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
