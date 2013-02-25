package by.bsu.mg.math.exceptions.matrices;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class IllegalMatrixSizeException extends RuntimeException {
    private int n;
    private int m;
    private final String msg = "Illegal matrix size n = %d m = %d";

    public IllegalMatrixSizeException(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public String toString() {
        return String.format(msg, n, m);
    }

}
