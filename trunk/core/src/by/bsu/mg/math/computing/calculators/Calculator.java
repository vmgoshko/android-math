package by.bsu.mg.math.computing.calculators;

import by.bsu.mg.math.computing.Borders;
import by.bsu.mg.math.computing.MathExtended;
import by.bsu.mg.math.parsing.lexemes.IError;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.utils.IPoint;

import java.util.*;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Calculator  {
    protected final static int NO_ARG = 0;
    protected static double step = 0.1;
    protected List<IPoint> points = new LinkedList<>();
    protected Queue<IError> errors = new ArrayDeque<>();
    protected Map<String, Double> vars = new HashMap<>();
    protected Map<String, Borders> borders;

    public Calculator()
    {

    }

    public Calculator(Map<String, Borders> borders) {
        this.borders = borders;
        Set<String> keys = borders.keySet();

        for (String key : keys) {
            vars.put(key,borders.get(key).BEGIN);
        }
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
