package by.bsu.mg.math.computing.calculators;

import by.bsu.mg.math.computing.MathExtended;
import by.bsu.mg.math.exceptions.computing.UnknownNodeTypeException;
import by.bsu.mg.math.parsing.expressions.nodes.DoubleNode;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.expressions.nodes.StringNode;
import by.bsu.mg.math.parsing.lexemes.IError;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Vladimir Goshko uhoshka@exadel.com
 */
public class XVarCalculator {
    private final static int NO_ARG = 0;
    private static double step = 0.1;
    private List<IPoint> points = new LinkedList<IPoint>();
    private Queue<IError> errors = new ArrayDeque<IError>();

    /**
     * Use this method to calculate value of constant expression,
     * if expression has any variables its value will be 0
     *
     * @param root : constant expression to calculate
     * @return double value     : constant expression value
     * @see Node
     */
    public double calculate(Node root) {
        return calculate(root, NO_ARG);
    }

    /**
     * Use this method to calculate value of expression for each x value from [xMin, xMax] range.
     *
     * @param root : expression to calculate
     * @param xMin : start x
     * @param xMax : finish x
     * @return List<Point2d>    : list of expression values for each x value from [xMin, xMax] range
     * @see Node
     * @see Point2d
     * @see java.util.List
     */
    public List<IPoint> calculate(Node root, double xMin, double xMax) {
        double xVal = xMin;

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

    private double calculateOneArgOperation(String name, LexemeType type, double arg) {
        switch (type) {
            case FUNCTION:
                switch (name) {
                    case "abs":
                        return Math.abs(arg);

                    case "sin":
                        return Math.sin(arg);

                    case "cos":
                        return Math.cos(arg);

                    case "tg":
                        return Math.tan(arg);

                    case "ctg":
                        return 1 / Math.tan(arg);

                    case "arsin":
                        return Math.asin(arg);

                    case "arcos":
                        return Math.acos(arg);

                    case "artg":
                        return Math.atan(arg);

                    case "arctg":
                        return Math.tan(arg);

                    case "ln":
                        return Math.log(arg);

                    case "lg":
                        return Math.log10(arg);

                    case "log2":
                        return Math.log(arg) / Math.log(2);

                    case "sh":
                        return Math.sinh(arg);

                    case "ch":
                        return Math.cosh(arg);

                    case "th":
                        return Math.tanh(arg);

                    case "cth":
                        return 1 / Math.tanh(arg);

                    case "todeg":
                        return Math.toDegrees(arg);

                    case "torad":
                        return Math.toRadians(arg);

                    case "floor":
                        return Math.floor(arg);

                    case "ceil":
                        return Math.ceil(arg);

                }
                double[] childrenValues = {arg};
                return calculateManyArgOperation(name, type, childrenValues);
            case UNARY_MINUS:
                return -arg;
            case UNARY_FACTORIAL:
                break;
            default:
                break;
        }


        return 0;
    }

    private double calculateTwoArgOperation(String name, LexemeType type, double larg, double rarg) {
        switch (type) {
            case BINARY_PLUS:
                return larg + rarg;

            case BINARY_MINUS:
                return larg - rarg;

            case BINARY_MULTIPLY:
                return larg * rarg;

            case BINARY_DIVIDE:
                return larg / rarg;

            case BINARY_POWER:
                return Math.pow(larg, rarg);

            case BINARY_MOD:
                return larg % rarg;
            case FUNCTION:
                if ("logd".equals(name)) {
                    return Math.log(rarg) / Math.log(larg);
                }

                double[] childrenValues = {larg, rarg};
                return calculateManyArgOperation(name, type, childrenValues);

        }

        return 0;
    }

    private double calculateManyArgOperation(String name, LexemeType type, double[] childrenValues) {
        switch (type) {
            case FUNCTION:
                if ("min".equals(name)) {
                    return MathExtended.findMin(childrenValues);
                }

                if ("max".equals(name)) {
                    return MathExtended.findMax(childrenValues);
                }
                break;
            default:
                break;
        }
        return 0;
    }
}
