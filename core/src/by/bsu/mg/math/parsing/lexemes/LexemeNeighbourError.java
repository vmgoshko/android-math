package by.bsu.mg.math.parsing.lexemes;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class LexemeNeighbourError implements IError {

    private Lexeme left;
    private Lexeme right;

    public LexemeNeighbourError(Lexeme left, Lexeme right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        String msg = "Lexeme %s with type %s can not be after lexeme %s with type %s";
        return String.format(msg,
                left.getValue(), right.getType().toString(),
                right.getValue(), left.getType().toString());
    }
}
