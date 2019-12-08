package src;

import src.parseTree.nodes.f_defn;
import src.parseTree.tokens.id;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class nameTableSingleton {
    private static nameTableSingleton instance;
    private static String filePath;

    private static Map<String, typeIdx> funType;
    private static Map<String, f_defn> funTable;
    private static Deque<dataFrame> stackFrame;

    public static void init_nameTable(String filepath) {
        instance = new nameTableSingleton();
        filePath = filepath;
    }

    private nameTableSingleton() {
        funType = new HashMap<>();
        funTable = new HashMap<>();
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

    public typeIdx getFunType(id name) {
        return funType.get(name.toString());
    }

    /**
     * Map integer value to identifier in local scope, used for initial assignments.
     *
     * @param name the identifier
     * @param val  the value
     */
    public void setInt(id name, Integer val) {
        dataFrame local = stackFrame.peekFirst();
        local.setInt(name, val);
    }

    /**
     * Map double value to identifier in local scope, used for initial assignments.
     *
     * @param name the identifier
     * @param val  the value
     */
    public void setDouble(id name, Double val) {
        dataFrame local = stackFrame.peekFirst();
        local.setDouble(name, val);
    }

    /**
     * Map string value to identifier in local scope, used for initial assignments.
     *
     * @param name the identifier
     * @param val  the value
     */
    public void setString(id name, String val) {
        dataFrame local = stackFrame.peekFirst();
        local.setString(name, val);
    }

    /**
     * Map integer value to immediate scope where identifier exists, used for reassignments.
     * Parsing phase ensures the identifier exists.
     *
     * @param name the identifier
     * @param val  the value
     */
    public void resetInt(id name, Integer val) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.isAssigned(name)) local.setInt(name, val);
        else global.setInt(name, val);
    }

    /**
     * Map double value to immediate scope where identifier exists, used for reassignments.
     * Parsing phase ensures the identifier exists.
     *
     * @param name the identifier
     * @param val  the value
     */
    public void resetDouble(id name, Double val) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.isAssigned(name)) local.setDouble(name, val);
        else global.setDouble(name, val);
    }

    /**
     * Map string value to immediate scope where identifier exists, used for reassignments.
     * Parsing phase ensures the identifier exists.
     *
     * @param name the identifier
     * @param val  the value
     */
    public void resetString(id name, String val) {
        dataFrame local = stackFrame.peekFirst();
        dataFrame global = stackFrame.peekLast();
        if (local.isAssigned(name)) local.setString(name, val);
        else global.setString(name, val);
    }

    public f_defn getFun(id name) {
        return funTable.get(name.toString());
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

    public void addStack(dataFrame dF) {
        stackFrame.push(dF);
    }

    public void popStack() {
        stackFrame.pop();
    }

    public void mapFun(id name, f_defn fun) {
        funType.put(name.toString(), fun.getType());
        funTable.put(name.toString(), fun);
    }
}

