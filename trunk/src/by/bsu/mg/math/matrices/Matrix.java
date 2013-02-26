package by.bsu.mg.math.matrices;

import by.bsu.mg.math.exceptions.matrices.IllegalMatrixSizeException;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 28.10.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
public class Matrix {
    private double elements[][];
    private int lineAmount;
    private int columnAmount;

    public Matrix(int m, int n) throws IllegalMatrixSizeException {
        if (m <= 0 || n <= 0) {
            throw new IllegalMatrixSizeException(m, n);
        }

        lineAmount = m; // exceptions should be written
        columnAmount = n;
        elements = new double[lineAmount][columnAmount];

    }

    public double[][] getElements() {
        return elements;
    }

    public double getElement(int i, int j) throws ArrayIndexOutOfBoundsException {
        if (i > elements.length || i < 0) {
            throw new ArrayIndexOutOfBoundsException("Illegal matrix row index:" + i);
        }

        if (j > elements[i].length || j < 0) {
            throw new ArrayIndexOutOfBoundsException("Illegal matrix column index: " + j);
        }

        return elements[i][j];
    }

    public void setElement(int i, int j, double value) throws ArrayIndexOutOfBoundsException {
        if (i > elements.length || i < 0) {
            throw new ArrayIndexOutOfBoundsException("Illegal matrix row index:" + i);
        }

        if (j > elements[i].length || j < 0) {
            throw new ArrayIndexOutOfBoundsException("Illegal matrix column index: " + j);
        }

        elements[i][j] = value;
    }

    public int getColumnAmount() {
        return columnAmount;
    }

    public int getLineAmount() {
        return lineAmount;
    }
}
