package src.parseTree.nodes;

import src.nameTableSingleton;
import src.parseTree.categories.Type;
import src.parseTree.tokens.id;
import src.typeIdx;

public class p_lst extends node {
    private Type t;
    private id name;
    private p_lst next;

    public void fixChildren() {
        t = (Type) children.get(0);
        name = (id) children.get(1);
        // comma(2)
        if (children.size() == 4) next = (p_lst) children.get(3);
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
     */
    public void execute(fc_p_lst values) {
        this.fixChildren();

        nameTableSingleton nT = nameTableSingleton.getInstance();
        typeIdx type = this.getType();
        switch (type) {
            case k_Integer:
                nT.setInt(name, ((Integer) values.getFirst().execute()));
                break;
            case k_Double:
                nT.setDouble(name, ((Double) values.getFirst().execute()));
                break;
            case k_String:
                nT.setString(name, ((String) values.getFirst().execute()));
                break;
        }
        if (next != null) next.execute(values.getNext());
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
