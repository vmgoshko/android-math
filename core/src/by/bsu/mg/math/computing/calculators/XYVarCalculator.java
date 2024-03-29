package by.bsu.mg.math.computing.calculators;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.exceptions.computing.UnknownNodeTypeException;
import by.bsu.mg.math.parsing.expressions.nodes.DoubleNode;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.expressions.nodes.StringNode;
import by.bsu.mg.math.parsing.lexemes.IError;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point3d;

import java.util.*;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class XYVarCalculator /*extends TwoVarCalculator */ {
    private static final double NO_ARG = 0;
    public static int xCount = 50;
    public static int yCount = 50;
    private final Map<String, Borders> borders;
    private List<IPoint> points = new LinkedList<>();
    private Queue<IError> errors = new ArrayDeque<>();
    private double xStep;
    private double yStep;

    public XYVarCalculator(Map<String, Borders> borders) {
        this.borders = borders;
    }

    public double calculateNoArg(Node root) {
        if (root == null)
            throw new NullPointerException(this.getClass() + " root is null");
        return calculate(root, NO_ARG, NO_ARG);
    }

    /**
     * Use this method to calculate value of expression for each (x,y) value from [xMin, xMax]x[yMin, yMax] range.
     *
     * @param root : expression to calculate
     * @return List<Point3d>    : list of expression values for each x value from [xMin, xMax] range
     * @see Node
     * @see by.bsu.mg.math.utils.Point2d
     * @see java.util.List
     */
    public List<IPoint> calculate(Node root) {
        double xVal = borders.get("x").BEGIN;
        double xMax = borders.get("x").END;

        double yMin = borders.get("y").BEGIN;
        double yVal = yMin;
        double yMax = borders.get("y").END;

        xStep = (xMax - xVal) / (xCount - 1);
        yStep = (yMax - yVal) / (yCount - 1);

        while (xVal <= xMax) {
            while (yVal <= yMax) {
                points.add(new Point3d(xVal, yVal, calculate(root, xVal, yVal)));
                yVal += yStep;
            }
            xVal += xStep;
            yVal = yMin;
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
    public double calculate(Node root, double xVal, double yVal) {
        double result;
        LexemeType type = root.getType();

        if (type == LexemeType.UNDEFINED) {
            // errors.add(new NodeTypeError(root)));
            throw new UnknownNodeTypeException();
        }

        if (type == LexemeType.VARIABLE) {
            if ("x".equals(((StringNode) root).getValue())) {
                result = xVal;
            } else {
                result = yVal;
            }
        } else if (type == LexemeType.NUMBER) {
            result = ((DoubleNode) root).getValue();
        } else {
            List<Node> children = root.getChildren();

            switch (children.size()) {
                case 1:
                    result = calculateOneArgOperation(((StringNode) root).getValue(),
                            type,
                            calculate(children.get(0), xVal, yVal));
                    break;
                case 2:
                    result = calculateTwoArgOperation(((StringNode) root).getValue(),
                            type,
                            calculate(root.getChild(0), xVal, yVal),
                            calculate(root.getChild(1), xVal, yVal));
                    break;
                default:
                    double[] childrenValues = new double[children.size()];

                    for (int i = 0; i < children.size(); i++) {
                        childrenValues[i] = calculate(children.get(i), xVal, yVal);
                    }
                    result = calculateManyArgOperation(((StringNode) root).getValue(), type, childrenValues);
                    break;
            }
        }

        return result;
    }

    protected double calculateOneArgOperation(String name, LexemeType type, double arg) {
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

    protected double calculateTwoArgOperation(String name, LexemeType type, double larg, double rarg) {
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

    protected double calculateManyArgOperation(String name, LexemeType type, double[] childrenValues) {
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
