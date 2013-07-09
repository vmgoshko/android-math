package by.bsu.mg.math.computing.calculators;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.List;
import java.util.Map;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class TVarCalculator extends XVarCalculator {

    public TVarCalculator() {

    }

    public TVarCalculator(Map<String, Borders> borders) {
        width = 100;
        this.borders = borders;
        this.step = (borders.get("t").END - borders.get("t").BEGIN) / width;
        createVars(borders);
    }

    public double calculateNoArg(Node root) {
        if (root == null)
            throw new NullPointerException(this.getClass() + " root is null");

        return calculate(root, NO_ARG);
    }

    /**
     * Use this method to calculate value of expression for each t value from [tMin, tMax] range.
     *
     * @param root : expression to calculate
     * @return List<Point2d>    : list of expression values for each x value from [tMin, tMax] range
     * @see Node
     * @see by.bsu.mg.math.utils.Point2d
     * @see java.util.List
     */
    public List<IPoint> calculate(Node root) {
        points.clear();
        errors.clear();
        createVars(borders);

        double xVal = borders.get("t").BEGIN;
        double xMax = borders.get("t").END;

        this.step = (xMax - xVal) / width;
        while (xVal <= xMax) {
            points.add(new Point2d(xVal, calculate(root, xVal)));
            xVal += step;
        }

        return points;
    }
}
