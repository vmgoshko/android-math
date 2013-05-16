package by.bsu.mg.math.computing.calculators;

import by.bsu.mg.math.computing.Borders;
import by.bsu.mg.math.computing.MathExtended;
import by.bsu.mg.math.exceptions.computing.UnknownNodeTypeException;
import by.bsu.mg.math.parsing.expressions.nodes.DoubleNode;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.expressions.nodes.StringNode;
import by.bsu.mg.math.parsing.lexemes.IError;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.*;

/**
 * @author Vladimir Goshko uhoshka@exadel.com
 */
public class XVarCalculator extends Calculator {
    private List<IPoint> points = new LinkedList<IPoint>();
    private Queue<IError> errors = new ArrayDeque<IError>();

    public XVarCalculator()
    {

    }

    public XVarCalculator(Map<String, Borders> borders) {
        super(borders);
    }


    public double calculateNoArg(Node root)
    {
        if(root == null)
            throw new NullPointerException(this.getClass() + " root is null");
        return calculate(root,NO_ARG);
    }


    /**
     * Use this method to calculate value of expression for each x value from [xMin, xMax] range.
     *
     * @param root : expression to calculate
     * @return List<Point2d>    : list of expression values for each x value from [xMin, xMax] range
     * @see Node
     * @see Point2d
     * @see java.util.List
     */
    public List<IPoint> calculate(Node root) {
        double xVal = vars.get("x");
        double xMax = borders.get("x").END;

        while (xVal <= xMax) {
            points.add(new Point2d(xVal, calculate(root, xVal)));
            xVal += step;
        }

        return points;
    }

    /**
     * Recursive method that runs over expression tree and calculate expression value for xVal.
     *
     * @param root : expression to calculate
     * @param :    x value
     * @return : expression value for xVal
     * @see Node
     */
    public double calculate(Node root, double xVal) {
        double result;
        LexemeType type = root.getType();

        if (type == LexemeType.UNDEFINED) {
            //    errors.add(new NodeTypeError(root)));
            throw new UnknownNodeTypeException();
        }

        if (type == LexemeType.VARIABLE) {
            result = xVal;
        } else if (type == LexemeType.NUMBER) {
            result = ((DoubleNode) root).getValue();
        } else {
            List<Node> children = root.getChildren();

            switch (children.size()) {
                case 1:
                    result = calculateOneArgOperation(((StringNode) root).getValue(),
                            type,
                            calculate(children.get(0), xVal));
                    break;
                case 2:
                    result = calculateTwoArgOperation(((StringNode) root).getValue(),
                            type,
                            calculate(root.getChild(0), xVal),
                            calculate(root.getChild(1), xVal));
                    break;
                default:
                    double[] childrenValues = new double[children.size()];

                    for (int i = 0; i < children.size(); i++) {
                        childrenValues[i] = calculate(children.get(i), xVal);
                    }
                    result = calculateManyArgOperation(((StringNode) root).getValue(), type, childrenValues);
                    break;
            }
        }

        return result;
        //return MathExtended.round(result);
    }
}
