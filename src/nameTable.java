package src;

import src.parseTree.tokens.id;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class nameTable {
    private static final int type_table_idx = 0;
    private static final int int_table_idx = 1;
    private static final int double_table_idx = 2;
    private static final int str_table_idx = 3;

    static public List<HashMap> makeNameTable() {
        List<HashMap> toReturn = new LinkedList<>();

        toReturn.add(type_table_idx, new HashMap<String, typeIdx>());
        toReturn.add(int_table_idx, new HashMap<String, Integer>());
        toReturn.add(double_table_idx, new HashMap<String, Double>());
        toReturn.add(str_table_idx, new HashMap<String, String>());

        return toReturn;
    }

    static public boolean isAssigned(List<HashMap> mapList, id name) {
        return mapList.get(type_table_idx).get(name.toString()) != null;
    }

    static public boolean isInteger(List<HashMap> mapList, id name) {
        return mapList.get(type_table_idx).get(name.toString()) == typeIdx.k_Integer;
    }

    static public boolean isDouble(List<HashMap> mapList, id name) {
        return mapList.get(type_table_idx).get(name.toString()) == typeIdx.k_Double;
    }

    static public boolean isString(List<HashMap> mapList, id name) {
        return mapList.get(type_table_idx).get(name.toString()) == typeIdx.k_String;
    }

    static public Integer getInt(List<HashMap> mapList, id name) {
        return (Integer) mapList.get(int_table_idx).get(name.toString());
    }

    static public Double getDouble(List<HashMap> mapList, id name) {
        return (Double) mapList.get(double_table_idx).get(name.toString());
    }

    static public String getString(List<HashMap> mapList, id name) {
        return (String) mapList.get(str_table_idx).get(name.toString());
    }

    static public void addInteger(List<HashMap> mapList, id name, Integer val) {
        if (isAssigned(mapList, name)) {
            //TODO: Reassignment watchdog for part 1
            return;
        }

        mapList.get(type_table_idx).put(name.toString(), typeIdx.k_Integer);
        mapList.get(int_table_idx).put(name.toString(), val);
    }

    static public void addDouble(List<HashMap> mapList, id name, Double val) {
        if (isAssigned(mapList, name)) {
            //TODO: Reassignment watchdog for part 1
            return;
        }

        mapList.get(type_table_idx).put(name.toString(), typeIdx.k_Double);
        mapList.get(double_table_idx).put(name.toString(), val);

    }

    static public void addString(List<HashMap> mapList, id name, String val) {
        if (isAssigned(mapList, name)) {
            //TODO: Reassignment watchdog for part 1
            return;
        }

        mapList.get(type_table_idx).put(name.toString(), typeIdx.k_String);
        mapList.get(str_table_idx).put(name.toString(), val);

    }

}

