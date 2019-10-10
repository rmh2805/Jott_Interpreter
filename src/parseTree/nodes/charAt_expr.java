package src.parseTree.nodes;

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
    }

    public List<Object> getChildren() {
        return children;
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
