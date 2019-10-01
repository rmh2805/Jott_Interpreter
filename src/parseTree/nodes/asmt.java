package src.parseTree.nodes;

import java.util.ArrayList;
import java.util.List;

public class asmt extends stmt<Integer> implements node {
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
    }

    public String getType() {
        return children.get(0).toString();
    }

    public String getId() {
        return children.get(1).toString();
    }

    @Override
    public Integer execute() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
