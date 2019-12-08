package src;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.parseTree.tokens.id;

import java.util.HashMap;
import java.util.Map;

public class dataFrame {
    private Map<String, typeIdx> typeMap = new HashMap<>();
    private Map<String, Integer> intMap = new HashMap<>();
    private Map<String, Double> doubleMap = new HashMap<>();
    private Map<String, String> strMap = new HashMap<>();

    boolean isAssigned(id name) {
        return typeMap.get(name.toString()) != null;
    }

    public typeIdx getType(id name) {
        return typeMap.get(name.toString());
    }

    public void setInt(id name, Integer val) {
        if (!isAssigned(name))
            typeMap.put(name.toString(), typeIdx.k_Integer);
        else if (getType(name) != typeIdx.k_Integer)
            errorPrinter.throwError(name, new Syntax("Incompatible types, requires Integer"));
        intMap.put(name.toString(), val);
    }

    public void setDouble(id name, Double val) {
        if (!isAssigned(name))
            typeMap.put(name.toString(), typeIdx.k_Double);
        else if (getType(name) != typeIdx.k_Double)
            errorPrinter.throwError(name, new Syntax("Incompatible types, requires Double"));
        doubleMap.put(name.toString(), val);
    }

    public void setString(id name, String val) {
        if (!isAssigned(name))
            typeMap.put(name.toString(), typeIdx.k_String);
        else if (getType(name) != typeIdx.k_String)
            errorPrinter.throwError(name, new Syntax("Incompatible types, requires String"));
        strMap.put(name.toString(), val);
    }

    Integer getInt(id name) {
        return intMap.get(name.toString());
    }

    Double getDouble(id name) {
        return doubleMap.get(name.toString());
    }

    String getString(id name) {
        return strMap.get(name.toString());
    }
}
