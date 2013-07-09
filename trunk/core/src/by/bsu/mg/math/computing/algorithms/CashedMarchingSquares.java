package by.bsu.mg.math.computing.algorithms;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.computing.calculators.XYVarCalculator;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class CashedMarchingSquares {
    public int cashAccessCounter;
    private Borders xBorders;
    private Borders yBorders;
    private double xStep;
    private double yStep;
    private int maxLevel;
    private int minLevel;
    private XYVarCalculator calc = new XYVarCalculator(null);
    private List<Point2d> points = new LinkedList<>();
    private Node root;
    private HashMap<Point2d, Double> cash = new HashMap<>();

    public CashedMarchingSquares(Node root, Borders xBorders, Borders yBorders, int maxLevel, int minLevel) {
        this.root = root;
        this.xBorders = xBorders;
        this.yBorders = yBorders;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
        this.xStep = (xBorders.END - xBorders.BEGIN) / Math.pow(2, maxLevel);
        this.yStep = (yBorders.END - yBorders.BEGIN) / Math.pow(2, maxLevel);
    }

    public List<? extends IPoint> startMarch() {
        cashAccessCounter = 0;
        points.clear();
        cash.clear();
        march(0, xBorders, yBorders);
        return points;
    }

    public void march(int level, Borders xBrds, Borders yBrds) {
        double valueLB, valueRB, valueRT, valueLT;
        Point2d pt = new Point2d(0, 0);
        Double cashVal;

        if (level == maxLevel) {
            pt.setCoords(xBrds.BEGIN, yBrds.BEGIN);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueLB = cashVal;
                cashAccessCounter++;
            } else {
                valueLB = calc.calculate(root, xBrds.BEGIN, yBrds.BEGIN);
                cash.put(pt, valueLB);
            }

            pt.setCoords(xBrds.END, yBrds.BEGIN);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueRB = cashVal;
                cashAccessCounter++;
            } else {
                valueRB = calc.calculate(root, xBrds.END, yBrds.BEGIN);
                cash.put(pt, valueRB);
            }

            pt.setCoords(xBrds.END, yBrds.END);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueRT = cashVal;
                cashAccessCounter++;
            } else {
                valueRT = calc.calculate(root, xBrds.END, yBrds.END);
                cash.put(pt, valueRT);
            }

            pt.setCoords(xBrds.BEGIN, yBrds.END);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueLT = cashVal;
                cashAccessCounter++;
            } else {
                valueLT = calc.calculate(root, xBrds.BEGIN, yBrds.END);
                cash.put(pt, valueLT);
            }

            int squareIndex = 0;

            if (valueLB >= 0) {
                squareIndex += 1;
            }


            if (valueRB >= 0) {
                squareIndex += 2;
            }


            if (valueRT >= 0) {
                squareIndex += 4;
            }


            if (valueLT >= 0) {
                squareIndex += 8;
            }

            if (squareIndex > 7)
                squareIndex = 15 - squareIndex;

            pushLines(squareIndex, xBrds, yBrds, valueLB, valueRB, valueRT, valueLT);

        } else {
            pt.setCoords(xBrds.BEGIN, yBrds.BEGIN);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueLB = cashVal;
                cashAccessCounter++;
            } else {
                valueLB = calc.calculate(root, xBrds.BEGIN, yBrds.BEGIN);
                cash.put(pt, valueLB);
            }

            pt.setCoords(xBrds.END, yBrds.BEGIN);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueRB = cashVal;
                cashAccessCounter++;
            } else {
                valueRB = calc.calculate(root, xBrds.END, yBrds.BEGIN);
                cash.put(pt, valueRB);
            }

            pt.setCoords(xBrds.END, yBrds.END);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueRT = cashVal;
                cashAccessCounter++;
            } else {
                valueRT = calc.calculate(root, xBrds.END, yBrds.END);
                cash.put(pt, valueRT);
            }

            pt.setCoords(xBrds.BEGIN, yBrds.END);
            cashVal = cash.get(pt);
            if (cashVal != null) {
                valueLT = cashVal;
                cashAccessCounter++;
            } else {
                valueLT = calc.calculate(root, xBrds.BEGIN, yBrds.END);
                cash.put(pt, valueLT);
            }


            if ((valueLB >= 0 && valueLT >= 0 && valueRB >= 0 && valueRT >= 0) ||
                    (valueLB < 0 && valueLT < 0 && valueRB < 0 && valueRT < 0)) {

                if (level > minLevel)
                    return;
            }

            march(level + 1,
                    new Borders(xBrds.BEGIN, (xBrds.BEGIN + xBrds.END) / 2),
                    new Borders(yBrds.BEGIN, (yBrds.BEGIN + yBrds.END) / 2));

            march(level + 1,
                    new Borders((xBrds.BEGIN + xBrds.END) / 2, xBrds.END),
                    new Borders(yBrds.BEGIN, (yBrds.BEGIN + yBrds.END) / 2));

            march(level + 1,
                    new Borders((xBrds.BEGIN + xBrds.END) / 2, xBrds.END),
                    new Borders((yBrds.BEGIN + yBrds.END) / 2, yBrds.END));

            march(level + 1,
                    new Borders(xBrds.BEGIN, (xBrds.BEGIN + xBrds.END) / 2),
                    new Borders((yBrds.BEGIN + yBrds.END) / 2, yBrds.END));
        }
    }

    private void pushLines(int squareIndex, Borders xBrds, Borders yBrds, double valueLB, double valueRB, double valueRT, double valueLT) {
        if (squareIndex == 0)
            return;

        if (squareIndex == 5) {
            if (calc.calculate(root,
                    (xBrds.BEGIN + xBrds.END) / 2,
                    (yBrds.BEGIN + yBrds.END) / 2) < 0) {
                squareIndex = 8;
            }
        }

        List<Point2d> template = template(squareIndex, valueLB, valueRB, valueRT, valueLT);

        for (int k = 0; k < template.size(); k++) {
            points.add(new Point2d(
                    template.get(k).getX() + xBrds.BEGIN,
                    template.get(k).getY() + yBrds.BEGIN));
        }

    }

    private List<Point2d> template(int squareIndex, double LB, double RB, double RT, double LT) {
        List<Point2d> tempList = null;
        if (squareIndex == 0)
            return null;

        LB = Math.abs(LB);
        RB = Math.abs(RB);
        RT = Math.abs(RT);
        LT = Math.abs(LT);

        switch (squareIndex) {
            case 1:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(LB / (RB + LB) * xStep, 0));
                tempList.add(new Point2d(0, LB / (LB + LT) * yStep));
                break;
            case 2:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(LB / (RB + LB) * xStep, 0));
                tempList.add(new Point2d(xStep, RB / (RB + RT) * yStep));
                break;
            case 3:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(0, LB / (LB + LT) * yStep));
                tempList.add(new Point2d(xStep, RB / (RT + RB) * yStep));
                break;
            case 4:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(xStep, RB / (RB + RT) * yStep));
                tempList.add(new Point2d(LT / (RT + LT) * xStep, yStep));
                break;
            case 5:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(xStep, RB / (RB + RT) * yStep));
                tempList.add(new Point2d(LT / (RT + LT) * xStep, yStep));
                tempList.add(new Point2d(LB / (RB + LB) * xStep, 0));
                tempList.add(new Point2d(0, LB / (LB + LT) * yStep));
                break;
            case 6:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(LB / (LB + RB) * xStep, 0));
                tempList.add(new Point2d(LT / (LT + RT) * xStep, yStep));
                break;
            case 7:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(0, LB / (LB + LT) * yStep));
                tempList.add(new Point2d(LT / (LT + RT) * xStep, yStep));
                break;
            case 8:
                tempList = new LinkedList<>();
                tempList.add(new Point2d(xStep, RB / (RB + RT) * yStep));
                tempList.add(new Point2d(LB / (RB + LB) * xStep, 0));
                tempList.add(new Point2d(LT / (RT + LT) * xStep, yStep));
                tempList.add(new Point2d(0, LB / (LB + LT) * yStep));
                break;
        }
        return tempList;
    }

    public void setBorders(Borders xBorders, Borders yBorders) {
        this.xBorders = xBorders;
        this.yBorders = yBorders;
        this.xStep = (xBorders.END - xBorders.BEGIN) / Math.pow(2, maxLevel);
        this.yStep = (yBorders.END - yBorders.BEGIN) / Math.pow(2, maxLevel);
    }
}
