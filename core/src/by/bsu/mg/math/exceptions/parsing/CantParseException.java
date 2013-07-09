package by.bsu.mg.math.exceptions.parsing;

import by.bsu.mg.math.parsing.lexemes.IError;

import java.util.Queue;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class CantParseException extends RuntimeException {
    private final Queue<IError> errors;
    private final String expr;
    private final String msg = "Can't parse expression %s because of errors:";
    private final String errorTemplate = "%s\n";

    public CantParseException(String expr, Queue<IError> errors) {
        this.errors = errors;
        this.expr = expr;
    }

    public String toString() {
        StringBuilder errorMsg = new StringBuilder(String.format(msg, expr));
        for (int i = 0; i < errors.size(); i++) {
            IError error = errors.poll();
            errorMsg.append(error + ";\n");
            errors.add(error);
        }

        return errorMsg.toString();
    }

}
