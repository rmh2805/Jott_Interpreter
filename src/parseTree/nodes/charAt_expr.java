package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.nameTableSingleton;
import src.parseTree.categories.str_val;
import src.parseTree.tokens.*;
import src.typeIdx;

public class charAt_expr extends str_expr {
    private charAt_label op;
    private start_paren startParen;
    private str_val strExpr;
    private comma sep;
    private int_expr intExpr;
    private end_paren endParen;

    public charAt_expr(charAt_label op, start_paren startParen, str_val strExpr, comma sep, int_expr intExpr, end_paren endParen) {
        this.op = op;
        this.startParen = startParen;
        this.strExpr = strExpr;
        this.sep = sep;
        this.intExpr = intExpr;
        this.endParen = endParen;
    }

    @Override
    public String execute() {
        String arg1 = null;
        Integer arg2 = intExpr.execute();

        //Get the value of the string argument
        if (strExpr instanceof id) {
            id strExpr = (id) this.strExpr;
            nameTableSingleton nameTable = nameTableSingleton.getInstance();

            if (!nameTable.isAssigned(strExpr))
                errorPrinter.throwError(strExpr.getLineNumber(), new Runtime("Reference to an unassigned id"));
            else if (nameTable.getType(strExpr) != typeIdx.k_String)
                errorPrinter.throwError(strExpr.getLineNumber(), new Runtime("id '" + strExpr.toString() + "' is not a String as required"));

            arg1 = nameTable.getString(strExpr);
        }
        else {
            str_expr strExpr = (str_expr) this.strExpr;
            arg1 = strExpr.execute();
        }

        if (arg2 >= arg1.length())
            errorPrinter.throwError(intExpr.getLineNumber(), new Runtime("Trying to access an index (" + arg2 + ") outside of the provided string"));

        return Character.toString(arg1.charAt(arg2));
    }

    @Override
    public String toString() {
        return op.toString() + startParen.toString() + strExpr.toString() + sep.toString() + intExpr.toString() + endParen.toString();
    }
}
