package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import src.parseTree.tokens.comma;
import src.parseTree.tokens.concat_label;
import src.parseTree.tokens.end_paren;
import src.parseTree.tokens.start_paren;

import java.util.ArrayList;
import java.util.List;

public class concat_expr extends str_expr implements str_val {
    private concat_label op;
    private start_paren startParen;
    private str_val lStr;
    private comma sep;
    private str_val rStr;
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
        this.concat_expr_set((concat_label) children.get(0), (start_paren) children.get(1),
                (str_val) children.get(2), (comma) children.get(3), (str_val) children.get(4), (end_paren) children.get(5));
    }

    public concat_expr()  {}

    public void concat_expr_set(concat_label op, start_paren startParen, str_val lStr, comma sep, str_val rStr, end_paren endParen) {
        this.op = op;
        this.startParen = startParen;
        this.lStr = lStr;
        this.sep = sep;
        this.rStr = rStr;
        this.endParen = endParen;
    }

    @Override
    public String execute() {
        return str_val.execute(lStr).concat(str_val.execute(rStr));
    }

    @Override
    public String toString() {
        return op.toString() + startParen.toString() + lStr.toString() + sep.toString() + rStr.toString() + endParen.toString();
    }
}
