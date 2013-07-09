package by.bsu.mg.math.exceptions.matrices;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class IncompatibleMatrixSize extends RuntimeException {
    private int firstRowsAmount;
    private int firstColumnsAmount;
    private int secondRowsAmount;
    private int secondColumnsAmount;
    private final String msg = "Incompatible matrices sizes: %dx%d and %dx%d";

    public IncompatibleMatrixSize(int n, int m, int i, int j) {
        firstRowsAmount = n;
        firstColumnsAmount = m;
        secondColumnsAmount = i;
        secondColumnsAmount = j;
    }

    public String toString() {
        return String.format(msg, firstRowsAmount, firstColumnsAmount, secondRowsAmount, secondColumnsAmount);
    }

}
