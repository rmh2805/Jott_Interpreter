package src.parseTree.nodes;

import src.errorHandling.errorPrinter;
import src.errorHandling.types.Runtime;
import src.parseTree.tokens.id;

public class variable<type> implements terminal<type> {
    private final global_state_singleton global = global_state_singleton.getGlobalState();

    private final id token;
    private final Class type_;

    public variable(id token, Class<type> type_) {
        this.token = token;
        this.type_ = type_.getClass();
    }

    public type getValue() {
        Object varMaybe = global.lookUpVariable(token.toString());
        if (varMaybe == null) {
            errorPrinter.throwError(token.getLineNumber(), new Runtime("Variable not initialized."));
        } else if (!type_.isInstance(varMaybe)) {
            errorPrinter.throwError(token.getLineNumber(), new Runtime( "Type mismatch: variable type " + type_.toString() + " is storing a " + varMaybe.getClass().toString()));
        }
        return (type) varMaybe;
    }
}
