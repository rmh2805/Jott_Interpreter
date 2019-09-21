package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class parseSet {

    private static final String DELIMITER = "[^a-zA-Z0-9_,]+";
    private static final String FIRSTPATH = "src/FIRST.txt";
    private static final String PREDICTPATH = "src/PREDICT.txt";
    static Map<String, Map<String, Boolean>> FIRST = new HashMap<>();
    static Map<String, Map<String, List<String>>> PREDICT = new HashMap<>();

    /**
     * Populate FIRST or PREDICT data structure for LL parsing given file with
     * relevant information.
     *
     * @param filePath The filePath of the representation of parse set
     * @throws FileNotFoundException
     */
    private static void loadSet(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath)).useDelimiter(DELIMITER);
        List<String> columnNames = new ArrayList<>();
        while (sc.hasNextLine()) {
            String[] tokens = sc.nextLine().split(DELIMITER);
            for (String columnName : tokens) {
                if (columnName.length() > 0) columnNames.add(columnName);
            }
            if (columnNames.size() > 0) break;
        }
        if (filePath.equals(FIRSTPATH)) {
            while (sc.hasNext()) {
                String rowName = sc.next();
                Map<String, Boolean> values = new HashMap<>();
                FIRST.put(rowName, values);
                for (String columnName : columnNames) {
                    if (sc.hasNext()) values.put(columnName, sc.next().equals("1"));
                    else break;
                }
            }
        }
        if (filePath.equals(PREDICTPATH)) {
            while (sc.hasNext()) {
                String rowName = sc.next();
                Map<String, List<String>> values = new HashMap<>();
                PREDICT.put(rowName, values);
                for (String columnName : columnNames) {
                    if (sc.hasNext()) values.put(columnName, Arrays.asList(sc.next().split(",")));
                    else break;
                }
            }
        }
        sc.close();
    }

    /**
     * Populate both LL parse set data structures.
     *
     * @throws FileNotFoundException
     */
    static void loadParseSets() throws FileNotFoundException {
        loadSet(FIRSTPATH);
        loadSet(PREDICTPATH);
    }
}
