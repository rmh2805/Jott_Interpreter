package src.parseTree.nodes;

import java.util.HashMap;
import java.util.Map;

public class global_state_singleton {
    private static global_state_singleton instance = null;

    //
    // Global state shared throughout the process
    //
    private final Map<String, Object> variableLookUpTable;
    private final String filePath;

    private global_state_singleton(String filePath) {
        variableLookUpTable = new HashMap<>();
        this.filePath = filePath;
    }

    public static global_state_singleton initGlobalState(String filePath) {
        instance = new global_state_singleton(filePath);
        return instance;
    }

    public static global_state_singleton getGlobalState() {
        if (instance == null) {
            throw new RuntimeException("Please initialize global state before trying to get it.");
        }
        return instance;
    }

    /**
     * Look up value of a global variable
     * @param label name of the variable
     */
    public Object lookUpVariable(String label) {
        return variableLookUpTable.get(label);
    }

    /**
     * Set the value of a global variable
     * @param label name of the variable
     */
    public void setVariable(String label, Object value) {
        variableLookUpTable.put(label, value);
    }

    /**
     * Gets the file path of a class in Jott. This is created
     */
    public String getFilePath() {
        return filePath;
    }
}
