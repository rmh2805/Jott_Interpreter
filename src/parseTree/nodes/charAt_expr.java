package src.parseTree.nodes;

import java.util.ArrayList;
import java.util.List;

public class charAt_expr extends str_expr implements node {
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
