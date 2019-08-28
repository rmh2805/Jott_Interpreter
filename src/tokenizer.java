

import java.io.File;
import java.util.*;
import src.token;

public class tokenizer {
    
    public static void main (String[] args) throws Exception {
        if(args.length < 1) {
            System.out.println("Error: Specify a file to tokenize");
            System.exit(1);
        }
        
        Scanner sc = new Scanner(new File (args[0]));
        String line;
        
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            System.out.println(line);
            
        }
        
        
    }
}