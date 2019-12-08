package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class parseSet {

    private static final String DELIMITER = "[^a-zA-Z0-9_,]+";
    private static final String FIRSTPATH = "Text/FIRST";
    private static final String PREDICTPATH = "Text/PREDICT";
    private static final int MIN_FIRST_RULE = 2; // A -> B
    private static final int PREDICT_RULE_SIZE = 3; // A,B -> C,<...>
    static Map<String, Map<String, Boolean>> FIRST = new HashMap<>();
    static Map<String, Map<String, List<String>>> PREDICT = new HashMap<>();

    /**
     * Populate FIRST data structure for LL parsing with file
     * containing relevant information.
     *
     * @throws FileNotFoundException
     */
    private static void loadFirst() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(FIRSTPATH)).useDelimiter(DELIMITER);
        while (sc.hasNextLine()) {
            String[] symbols = sc.nextLine().split(DELIMITER);
            if (symbols.length >= MIN_FIRST_RULE) {
                String lhs = symbols[0];
                Map<String, Boolean> rhs = new HashMap<>();
                for (int i = 1; i < symbols.length; i++) rhs.put(symbols[i], true);
                FIRST.put(lhs, rhs);
            }
        }
        sc.close();
    }

    /**
     * Populate PREDICT data structure for LL parsing with file
     * containing relevant information.
     *
     * @throws FileNotFoundException
     */
    private static void loadPredict() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(PREDICTPATH)).useDelimiter(DELIMITER);
        while (sc.hasNextLine()) {
            String[] symbols = sc.nextLine().split(DELIMITER);
            if (symbols.length == PREDICT_RULE_SIZE) {
                String key = symbols[0];
                Map<String, List<String>> value = new HashMap<>();
                if (PREDICT.containsKey(key)) value = PREDICT.get(key);
                String key_ = symbols[1];
                value.put(key_,
                        Arrays.asList(symbols[2].split(",")));
                if (!PREDICT.containsKey(key)) PREDICT.put(key, value);
            }
        }
        sc.close();
    }

    /**
     * Populate both LL parse set data structures.
     */
    static void loadParseSets() {
        try {
            loadFirst();
            loadPredict();
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file path for parse set");
            System.exit(-1);
        }
    }
}
