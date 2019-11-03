package src.parseTree.categories;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.nodes.str_expr;
import src.parseTree.tokens.id;
import src.typeIdx;

public interface str_val {
    static String execute(str_val strVal) {
        if (strVal instanceof id) {
            id strExpr = (id) strVal;
            nameTableSingleton nameTable = nameTableSingleton.getInstance();

            if (!nameTable.isAssigned(strExpr))
                errorPrinter.throwError(strExpr, new Runtime("Reference to an unassigned id"));
            else if (nameTable.getType(strExpr) != typeIdx.k_String)
                errorPrinter.throwError(strExpr, new Runtime("id '" + strExpr.toString() + "' is not a String as required"));

            return nameTable.getString(strExpr);
        }
        else {
            str_expr strExpr = (str_expr) strVal;
            return strExpr.execute();
        }
    }

    public void fixChildren();
}
