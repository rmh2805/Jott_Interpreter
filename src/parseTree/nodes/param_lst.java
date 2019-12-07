package src.parseTree.nodes;

import src.dataFrame;
import src.errorHandling.errorPrinter;
import src.errorHandling.types.Syntax;
import src.nameTableSingleton;
import src.parseTree.categories.Type;
import src.parseTree.tokens.id;
import src.typeIdx;

public class param_lst extends node {
    private Type t;
    private id name;
    private param_lst next;

    public void fixChildren() {
        t = (Type) children.get(0);
        name = (id) children.get(1);
        // comma(2)
        if (children.size() == 4) next = (param_lst) children.get(3);
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

    /**
     * Add values provided by function call, mapped to param names of function defn.
     * Pre: 1) Function call parameter list matches function definition parameter list
     *      2) Stack for function created
     *
     * @param values the function call parameters
     * @param dF     the new dataframe
     */
    public void execute(f_call_param_lst values, dataFrame dF) {
        this.fixChildren();

        nameTableSingleton nT = nameTableSingleton.getInstance();
        expr param = values.getFirst();
        typeIdx type = this.getType();
        switch (type) {
            case k_Integer:
                dF.setInt(name, ((Integer) param.execute()));
                break;
            case k_Double:
                dF.setDouble(name, ((Double) param.execute()));
                break;
            case k_String:
                dF.setString(name, ((String) param.execute()));
                break;
        }

        if (next != null) {
            if (values.getNext() == null)
                errorPrinter.throwError(values.getLineNumber(), values.getIndex(), new Syntax("Missing arguments"));
            next.execute(values.getNext(), dF);
        } else {
            if (values.getNext() != null)
                errorPrinter.throwError(values.getNext().getLineNumber(), values.getNext().getIndex(), new Syntax("Too many arguments"));
        }
    }

    /**
     * The string representation of this parameter list
     *
     * @return the string representation of this parameter list
     */
    public String toString() {
        return String.format("%s %s%s", t.toString(), name.toString(),
                (next == null) ? "" : (", " + next.toString()));
    }

}
