package src.parseTree.nodes;

import java.util.ArrayList;
import java.util.List;

public class asmt extends stmt<Integer> implements node {
    private List<Object> children = new ArrayList<>();

    public void addChild(Object child) {
        children.add(child);
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
