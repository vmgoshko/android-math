package by.bsu.mg.math.matrices;

import by.bsu.mg.math.exceptions.matrices.IncompatibleMatrixSize;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 28.10.12
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class MatrixOperations {
    public Matrix sum(Matrix firstMatrix, Matrix secondMatrix) throws IncompatibleMatrixSize {
        if (firstMatrix.getLineAmount() != secondMatrix.getLineAmount() ||
                firstMatrix.getColumnAmount() != secondMatrix.getColumnAmount()) {

            throw new IncompatibleMatrixSize(firstMatrix.getLineAmount(), firstMatrix.getColumnAmount(),
                    secondMatrix.getLineAmount(), secondMatrix.getColumnAmount());
        }

        Matrix resMatrix = new Matrix(firstMatrix.getLineAmount(), firstMatrix.getColumnAmount());

        for (int i = 0; i < resMatrix.getLineAmount(); i++) {
            for (int j = 0; j < resMatrix.getColumnAmount(); j++) {
                resMatrix.setElement(i, j, firstMatrix.getElement(i, j) + secondMatrix.getElement(i, j));
            }
        }
        return resMatrix;
    }

    public Matrix diff(Matrix firstMatrix, Matrix secondMatrix) throws IncompatibleMatrixSize {
        if (firstMatrix.getLineAmount() != secondMatrix.getLineAmount() ||
                firstMatrix.getColumnAmount() != secondMatrix.getColumnAmount()) {

            throw new IncompatibleMatrixSize(firstMatrix.getLineAmount(), firstMatrix.getColumnAmount(),
                    secondMatrix.getLineAmount(), secondMatrix.getColumnAmount());
        }

        Matrix resMatrix = new Matrix(firstMatrix.getLineAmount(), firstMatrix.getColumnAmount());

        for (int i = 0; i < resMatrix.getLineAmount(); i++) {
            for (int j = 0; j < resMatrix.getColumnAmount(); j++) {
                resMatrix.setElement(i, j, firstMatrix.getElement(i, j) - secondMatrix.getElement(i, j));
            }
        }
        return resMatrix;
    }

    public Matrix production(Matrix firstMatrix, Matrix secondMatrix) {
        if (firstMatrix.getColumnAmount() != secondMatrix.getLineAmount()) {
            // exception should be thrown
        }

        Matrix resMatrix = new Matrix(firstMatrix.getLineAmount(), secondMatrix.getColumnAmount());

        double tempSumm = 0;

        for (int i = 0; i < firstMatrix.getLineAmount(); i++) {
            for (int k = 0; k < secondMatrix.getColumnAmount(); k++) {
                for (int j = 0; j < firstMatrix.getColumnAmount(); j++) {
                    tempSumm += firstMatrix.getElement(i, j) * secondMatrix.getElement(j, k);
                }
                resMatrix.setElement(i, k, tempSumm);
                tempSumm = 0;
            }
        }
        return resMatrix;
    }

    public double determinant(Matrix matrix) {
        double[][] tempElements = matrix.getElements();

        if (matrix.getColumnAmount() != matrix.getLineAmount()) {
            // exception should be thrown
        }

        int size = matrix.getColumnAmount();
        double det = 1;
        for (int i = 0; i < size; i++) {
            int tempIndex = i + 1;
            while (matrix.getElement(i, i) == 0) {
                swapLines(i, tempIndex, matrix);
                if (tempIndex == size - 1) return 0;
                tempIndex++;
            }

            det *= matrix.getElement(i, i);
            divideLine(i, matrix.getElement(i, i), matrix);

            for (int j = i + 1; j < size; j++) {
                deductLinesWithCoef(i, j, matrix, matrix.getElement(j, i));
            }
        }
        return det;
    }

    private void swapLines(int first, int second, Matrix matrix) {
        double[] tempLine = new double[matrix.getColumnAmount()];

        for (int i = 0; i < matrix.getColumnAmount(); i++) {
            tempLine[i] = matrix.getElement(first, i);
            matrix.setElement(first, i, matrix.getElement(second, i));
            matrix.setElement(second, i, tempLine[i]);
        }
    }

    private void divideLine(int index, double value, Matrix matrix) {
        for (int i = 0; i < matrix.getColumnAmount(); i++) {
            matrix.setElement(index, i, matrix.getElement(index, i) / value);
        }
    }

    private void deductLinesWithCoef(int first, int second, Matrix matrix, double coef) {
        for (int i = 0; i < matrix.getColumnAmount(); i++) {
            matrix.setElement(second, i, matrix.getElement(second, i) - matrix.getElement(first, i) * coef);
        }
    }
}
