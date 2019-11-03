package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class parseSet {

    private static final String DELIMITER = "[^a-zA-Z0-9_,]+";
    private static final String FIRSTPATH = "Text/FIRST.txt";
    private static final String PREDICTPATH = "Text/PREDICT.txt";
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
        String[] columnNames = {};
        while (sc.hasNextLine()) { // get column names
            columnNames = sc.nextLine().split(DELIMITER);
            if (columnNames.length > 0) break;
        }
        if (filePath.equals(FIRSTPATH)) {
            while (sc.hasNext()) {
                String rowName = sc.next();
                Map<String, Boolean> values = new HashMap<>();
                for (String columnName : columnNames) {
                    if (columnName.length() <= 0) continue;
                    if (sc.hasNext()) values.put(columnName, sc.next().equals("1"));
                    else break;
                }
                FIRST.put(rowName, values);
            }
        }
        if (filePath.equals(PREDICTPATH)) {
            while (sc.hasNext()) {
                String rowName = sc.next();
                Map<String, List<String>> values = new HashMap<>();
                for (String columnName : columnNames) {
                    if (columnName.length() <= 0) continue;
                    if (sc.hasNext()) {
                        List<String> tmp = Arrays.asList(sc.next().split(","));
                        values.put(columnName, new ArrayList<>(tmp));
                    }
                    else break;
                }
                PREDICT.put(rowName, values);
            }
        }
        sc.close();
    }

    /**
     * Populate both LL parse set data structures.
     */
    static void loadParseSets() {
        try {
            loadSet(FIRSTPATH);
            loadSet(PREDICTPATH);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file path for parse set");
            System.exit(-1);
        }
    }
}
