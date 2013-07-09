package by.bsu.mg.math.parsing.lexemes;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class LexemeListValidator {

    private List<Lexeme> lexemes;
    private Queue<IError> errors;

    public LexemeListValidator(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
        errors = new ArrayDeque<IError>();
    }

    public boolean validate() {
        boolean isOK = true;
        for (int i = 0; i < lexemes.size() - 1; i++) {
            isOK &= checkNeighbour(i);
        }

        return isOK;
    }

    private boolean checkNeighbour(int pos) {
        Lexeme leftLexeme = lexemes.get(pos);
        Lexeme rightLexeme = lexemes.get(pos + 1);

        LexemeType leftType = leftLexeme.getType();
        LexemeType rightType = rightLexeme.getType();

        boolean valideNeighbour;

        switch (leftType) {
            case UNDEFINED:
                errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                return false;

            case OPEN_BRACKET:
                valideNeighbour = (rightType == LexemeType.OPEN_BRACKET) ||
                        (rightType == LexemeType.FUNCTION) ||
                        (rightType == LexemeType.UNARY_MINUS) ||
                        (rightType == LexemeType.NUMBER) ||
                        (rightType == LexemeType.VARIABLE);
                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;

            case CLOSE_BRACKET:
                valideNeighbour = (rightType == LexemeType.BINARY_PLUS) ||
                        (rightType == LexemeType.BINARY_MINUS) ||
                        (rightType == LexemeType.BINARY_MULTIPLY) ||
                        (rightType == LexemeType.BINARY_DIVIDE) ||
                        (rightType == LexemeType.BINARY_MOD) ||
                        (rightType == LexemeType.BINARY_POWER) ||
                        (rightType == LexemeType.CLOSE_BRACKET) ||
                        (rightType == LexemeType.UNARY_FACTORIAL) ||
                        (rightType == LexemeType.SEPARATOR);

                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;
            case SEPARATOR:
                valideNeighbour = (rightType == LexemeType.OPEN_BRACKET) ||
                        (rightType == LexemeType.UNARY_MINUS) ||
                        (rightType == LexemeType.FUNCTION) ||
                        (rightType == LexemeType.NUMBER) ||
                        (rightType == LexemeType.VARIABLE);
                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;
            case BINARY_PLUS:
            case BINARY_MINUS:
            case BINARY_DIVIDE:
            case BINARY_MULTIPLY:
            case BINARY_MOD:
            case BINARY_POWER:
                valideNeighbour = (rightType == LexemeType.OPEN_BRACKET) ||
                        (rightType == LexemeType.FUNCTION) ||
                        (rightType == LexemeType.NUMBER) ||
                        (rightType == LexemeType.VARIABLE);
                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;

            case UNARY_MINUS:
                valideNeighbour = (rightType == LexemeType.OPEN_BRACKET) ||
                        (rightType == LexemeType.FUNCTION) ||
                        (rightType == LexemeType.NUMBER) ||
                        (rightType == LexemeType.VARIABLE);
                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;

            case UNARY_FACTORIAL:
                valideNeighbour = (rightType == LexemeType.CLOSE_BRACKET) ||
                        //   (rightType == LexemeType.SEPARATOR) ||
                        (rightType == LexemeType.BINARY_PLUS) ||
                        (rightType == LexemeType.BINARY_MINUS) ||
                        (rightType == LexemeType.BINARY_MULTIPLY) ||
                        (rightType == LexemeType.BINARY_DIVIDE) ||
                        (rightType == LexemeType.BINARY_MOD) ||
                        (rightType == LexemeType.BINARY_POWER);
                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;

            case FUNCTION:
                valideNeighbour = (rightType == LexemeType.OPEN_BRACKET);
                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;
            case NUMBER:
            case VARIABLE:
                valideNeighbour = (rightType == LexemeType.CLOSE_BRACKET) ||
                        (rightType == LexemeType.SEPARATOR) ||
                        (rightType == LexemeType.BINARY_PLUS) ||
                        (rightType == LexemeType.BINARY_MINUS) ||
                        (rightType == LexemeType.BINARY_MULTIPLY) ||
                        (rightType == LexemeType.BINARY_DIVIDE) ||
                        (rightType == LexemeType.BINARY_MOD) ||
                        (rightType == LexemeType.BINARY_POWER);

                if (!valideNeighbour) {
                    errors.add(new LexemeNeighbourError(leftLexeme, rightLexeme));
                }
                return valideNeighbour;
        }
        return false;
    }

    public Queue<IError> getErrors() {
        return errors;
    }
}
