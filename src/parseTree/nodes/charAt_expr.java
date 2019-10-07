package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import src.parseTree.tokens.charAt_label;
import src.parseTree.tokens.comma;
import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.start_paren;

import java.util.ArrayList;
import java.util.List;

public class charAt_expr extends str_expr implements node {
    private List<Object> children = new ArrayList<>();

    charAt_label op;
    start_paren startParen;
    str_val strExpr;
    comma sep;
    int_expr intExpr;
    end_paren endParen;

    public void addChild(Object child) {
        children.add(child);
    }

    public void fixChildren() {
        //todo Assign the proper children to their fields
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
