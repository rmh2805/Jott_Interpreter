package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.tokens.id;

import java.util.HashMap;
import java.util.Map;

public class nameTableSingleton {
    private static nameTableSingleton instance;
    private static String filePath;

    private Map<String, typeIdx> typeMap;
    private Map<String, Integer> intMap;
    private Map<String, Double> doubleMap;
    private Map<String, String> strMap;

    /*
     *  For part 3, add a new table for function statement lists, as well as a stack of local variable maps
     */


    public static void init_nameTable(String filepath) {
        instance = new nameTableSingleton();
        filePath = filepath;
    }

    private nameTableSingleton() {
        typeMap = new HashMap<>();
        intMap = new HashMap<>();
        doubleMap = new HashMap<>();
        strMap = new HashMap<>();
    }

    public static nameTableSingleton getInstance() {
        return instance;
    }

    public static String getFilePath() {
        return filePath;
    }

    public boolean isAssigned(id name) {
        return typeMap.get(name.toString()) != null;
    }

    public typeIdx getType(id name) {
        return typeMap.get(name.toString());
    }

    public void setInt(id name, Integer val) {
        if (isAssigned(name))
            errorPrinter.throwError(name.getLineNumber(), new Syntax("Cannot reassign variables"));

        typeMap.put(name.toString(), typeIdx.k_Integer);
        intMap.put(name.toString(), val);
    }

    public void setDouble(id name, Double val) {
        if (isAssigned(name))
            errorPrinter.throwError(name.getLineNumber(), new Syntax("Cannot reassign variables"));

        typeMap.put(name.toString(), typeIdx.k_Double);
        doubleMap.put(name.toString(), val);
    }

    public void setString(id name, String val) {
        if (isAssigned(name))
            errorPrinter.throwError(name.getLineNumber(), new Syntax("Cannot reassign variables"));

        typeMap.put(name.toString(), typeIdx.k_String);
        strMap.put(name.toString(), val);
    }

    public Integer getInt(id name) {
        return intMap.get(name.toString());
    }

    public Double getDouble(id name) {
        return doubleMap.get(name.toString());
    }

    public String getString(id name) {
        return strMap.get(name.toString());
    }

}

