package src.parseTree.nodes;

import src.parseTree.categories.str_val;
import java.util.ArrayList;
import java.util.List;

public class concat_expr extends str_expr implements str_val, node {
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
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
