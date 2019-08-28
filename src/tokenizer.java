

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
        
        List<token> tokenList = new LinkedList<> ();
        
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            int lineLen = line.length();
            
            for (int i = 0; i < lineLen; i++) {
               char ch = line.charAt(i);
                if(Character.isWhitespace(ch)) {
                    System.out.print('_');
                } else if (ch == '"') {
                    //Start of a string literal
                    
                    
                    
                } else {
                    System.out.print(ch);
                }
                
            }
            System.out.print('\n');
            
        }
        
        
    }
}