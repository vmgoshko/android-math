package by.bsu.mg.math.computing.algorithms;

import android.util.Log;
import by.bsu.mg.math.computing.Borders;
import by.bsu.mg.math.computing.calculators.XYVarCalculator;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class BadMarchingSquares {
    private Borders xBorders;
    private Borders yBorders;
    private double xStep;
    private double yStep;
    private byte signes[][];
    private XYVarCalculator calc = new XYVarCalculator();
    private List<Point2d> points = new LinkedList<>();
    private List<Point2d> templates[] = new List[9];
    private Node root;

    public BadMarchingSquares(Node root, Borders xBorders, Borders yBorders, int xCount, int yCount) {
        this.root = root;
        this.xBorders = xBorders;
        this.yBorders = yBorders;

        this.xStep = (xBorders.END - xBorders.BEGIN) / xCount;
        this.yStep = (yBorders.END - yBorders.BEGIN) / yCount;
        this.signes = new byte[xCount][yCount];

        createTemplates(xStep, yStep);
    }

    private void createTemplates(double xStep, double yStep) {
        templates[0] = null;

        List<Point2d> tempList = new LinkedList<>();
        tempList.add(new Point2d(xStep / 2, 0));
        tempList.add(new Point2d(0, yStep / 2));
        templates[1] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(xStep / 2, 0));
        tempList.add(new Point2d(xStep, yStep / 2));
        templates[2] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(0, yStep / 2));
        tempList.add(new Point2d(xStep, yStep / 2));
        templates[3] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(xStep / 2, yStep));
        tempList.add(new Point2d(xStep, yStep / 2));
        templates[4] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(xStep / 2, yStep));
        tempList.add(new Point2d(xStep, yStep / 2));
        tempList.add(new Point2d(0, yStep / 2));
        tempList.add(new Point2d(xStep / 2, 0));
        templates[5] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(xStep / 2, yStep));
        tempList.add(new Point2d(xStep / 2, 0));
        templates[6] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(0, yStep / 2));
        tempList.add(new Point2d(xStep / 2, yStep));
        templates[7] = tempList;

        tempList = new LinkedList<>();
        tempList.add(new Point2d(xStep / 2, yStep));
        tempList.add(new Point2d(0, yStep / 2));
        tempList.add(new Point2d(xStep, yStep / 2));
        tempList.add(new Point2d(xStep / 2, 0));
        templates[8] = tempList;
    }

    public List<? extends IPoint> march() {
        for (int i = 0; i < signes.length; i++) {
            for (int j = 0; j < signes[i].length; j++) {
                if (signes[i][j] == 0) {
                   signes[i][j] = calc.calculate(root,
                            xBorders.BEGIN + i * xStep,
                            yBorders.BEGIN + j * yStep) >= 0 ? (byte) 1 : (byte) (-1);
                   /*signes[i][j] = (xBorders.BEGIN + i * xStep) * (xBorders.BEGIN + i * xStep) +
                            (yBorders.BEGIN + j * yStep) * (yBorders.BEGIN + j * yStep) - 9 >= 0 ? (byte) 1 : (byte) (-1);*/
                }
            }
        }

        int squareIndex;
        for (int i = 0; i < signes.length - 1; i++) {
            for (int j = 0; j < signes[i].length - 1; j++) {
                squareIndex = 0;

                if (signes[i][j] > 0) {
                    squareIndex += 1;
                }


                if (signes[i + 1][j] > 0) {
                    squareIndex += 2;
                }


                if (signes[i + 1][j + 1] > 0) {
                    squareIndex += 4;
                }


                if (signes[i][j + 1] > 0) {
                    squareIndex += 8;
                }

                if (squareIndex > 7)
                    squareIndex = 15 - squareIndex;

                pushLines(squareIndex, i, j);
            }
        }
        return points;
    }

    private void pushLines(int squareIndex, int i, int j) {
        if(squareIndex == 0)
            return;

        if (squareIndex == 5) {
            if (calc.calculate(root,
                    xBorders.BEGIN + (i + 0.5) * xStep,
                    yBorders.BEGIN + (j + 0.5) * yStep) < 0) {
                squareIndex = 8;
            }
        }

        List<Point2d> template = templates[squareIndex];

        for(int k = 0; k < template.size(); k++) {
            points.add(new Point2d(
                    template.get(k).getX() + xBorders.BEGIN + i* xStep,
                    template.get(k).getY() + yBorders.BEGIN + j * yStep));
        }

    }
}
