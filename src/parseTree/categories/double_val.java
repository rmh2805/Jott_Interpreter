package src.parseTree.categories;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.nodes.double_expr;
import src.parseTree.tokens.double_token;
import src.parseTree.tokens.id;
import src.typeIdx;

public interface double_val {
    static double execute(double_val dblVal) {
        if (dblVal instanceof id) {
            id dblExpr = (id) dblVal;
            nameTableSingleton nameTable = nameTableSingleton.getInstance();

            if (!nameTable.isAssigned(dblExpr))
                errorPrinter.throwError(dblExpr, new Runtime("Reference to an unassigned id"));
            else if (nameTable.getType(dblExpr) != typeIdx.k_Double)
                errorPrinter.throwError(dblExpr, new Runtime("id '" + dblExpr.toString() + "' is not a Double as required"));

            return nameTable.getDouble(dblExpr);
        }
        else if (dblVal instanceof double_token) {
            return ((double_token) dblVal).getVal();
        }
        else {
            double_expr dblExpr = (double_expr) dblVal;
            return dblExpr.execute();
        }
    }
}
