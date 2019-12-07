package src.parseTree.nodes;

import src.dataFrame;
import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.nameTableSingleton;
import src.parseTree.categories.Type;
import src.parseTree.tokens.id;
import src.typeIdx;

public class f_defn extends stmt<Integer> {
    private Type t;
    private id name;
    private param_lst params;
    private f_stmt_lst body;
    private node ret_val;

    public void fixChildren() {
        t = (Type) children.get(0);
        name = (id) children.get(1);
        // start_paren(2)
        if (children.get(3) instanceof param_lst) params = (param_lst) children.get(3);
        // end_paren, start_brace
        if (this.getType() == typeIdx.k_Void) {
            body = (f_stmt_lst) children.get(children.size() - 2);
        }
        else {
            body = (f_stmt_lst) children.get(children.size() - 3);
            ret_val = (node) children.get(children.size() - 2);
        }
        // end_brace
    }

    public String getId() {
        return children.get(1).toString();
    }

    public typeIdx getType() {
        switch (children.get(0).toString()) {
            case "Integer":
                return typeIdx.k_Integer;
            case "Double":
                return typeIdx.k_Double;
            case "String":
                return typeIdx.k_String;
            default:
                return typeIdx.k_Void;
        }
    }

    public param_lst getParams() {
        if (children.get(3) instanceof param_lst) return (param_lst) children.get(3);
        return null;
    }

    /**
     * Execute is called for function definition of the function, assigning the function
     * in the global name table singleton
     *
     * @return General exit status (0 is good)
     */
    public Integer execute() {
        this.fixChildren();
        // bind function definition to identifier in function table
        nameTableSingleton.getInstance().mapFun(name, this);
        return 0;
    }

    /**
     * This is called whenever this function is invoked in the Jott source code
     *
     * @param values The list of parameters to execute on
     * @return The returned value from this function call
     */
    Object call(f_call_param_lst values) {
        //Parameter validation
        if (params == null && values != null)
            errorPrinter.throwError(values.getLineNumber(), values.getIndex(), new Syntax("Too many arguments"));

        Object result = null;
        nameTableSingleton nT = nameTableSingleton.getInstance();   //Grabbing the name table once, we'll be back here a lot

        //Establish stack frame
        dataFrame dF = new dataFrame(); // Create a new stack frame for this function
        if (params != null && values != null)   //Prepopulate the new stack frame with our passed parameters
            params.execute(values, dF);
        nT.addStack(dF);


        //Execute code and extract the return value
        body.execute();
        if (ret_val instanceof int_return)
            result = ((int_return) ret_val).execute();
        else if (ret_val instanceof double_return)
            result = ((double_return) ret_val).execute();
        else if (ret_val instanceof str_return)
            result = ((str_return) ret_val).execute();

        //Cleanup and return
        nT.popStack();
        return result;
    }

    /**
     * The string representation of this function definition
     *
     * @return the string representation of this function definition
     */
    public String toString() {
        return String.format("%s %s(%s) {\n%s%s\n}", t.toString(), name.toString(),
                (params == null) ? "" : params.toString(),
                body.toString(),
                (ret_val == null) ? "" : ("\n" + ret_val.toString()));
    }

}
