package src;

import src.parseTree.tokens.id;

import java.util.ArrayDeque;
import java.util.Deque;

public class nameTableSingleton {
    private static nameTableSingleton instance;
    private static String filePath;

    private static Deque<dataFrame> stackFrame;

    public static void init_nameTable(String filepath) {
        instance = new nameTableSingleton();
        filePath = filepath;
    }

    private nameTableSingleton() {
        stackFrame = new ArrayDeque<>();
        dataFrame global = new dataFrame();
        stackFrame.add(global);
    }

    public static nameTableSingleton getInstance() {
        return instance;
    }

    public static String getFilePath() {
        return filePath;
    }

    public boolean isAssigned(id name) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        return local.isAssigned(name) || global.isAssigned(name);
    }

    public typeIdx getType(id name) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.isAssigned(name)) return local.getType(name);
        return global.getType(name);
    }

    public void setInt(id name, Integer val) {
        dataFrame local = stackFrame.peekFirst();
        local.setInt(name, val);
    }

    public void setDouble(id name, Double val) {
        dataFrame local = stackFrame.peekFirst();
        local.setDouble(name, val);
    }

    public void setString(id name, String val) {
        dataFrame local = stackFrame.peekFirst();
        local.setString(name, val);
    }

    public Integer getInt(id name) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.getType(name) == typeIdx.k_Integer) return local.getInt(name);
        else return global.getInt(name);
    }

    public Double getDouble(id name) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.getType(name) == typeIdx.k_Double) return local.getDouble(name);
        else return global.getDouble(name);
    }

    public String getString(id name) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.getType(name) == typeIdx.k_String) return local.getString(name);
        else return global.getString(name);
    }

}

