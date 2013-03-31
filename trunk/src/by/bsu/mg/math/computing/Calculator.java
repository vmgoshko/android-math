package by.bsu.mg.math.computing;

import by.bsu.mg.math.exceptions.IllegalArgumentsAmountException;
import by.bsu.mg.math.parsing.expressions.nodes.DoubleNode;
import by.bsu.mg.math.parsing.expressions.nodes.StringNode;
import by.bsu.mg.math.parsing.lexemes.IError;
import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.lexemes.LexemeEvaluator;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Vladimir Goshko uhoshka@exadel.com
 *
 */
public class Calculator {
    private List<IPoint> points = new LinkedList<IPoint>();
    private final static int NO_ARG = 0;
    private Queue<IError> errors = new ArrayDeque<IError>();

    /**
     * Use this method to calculate value of constant expression,
     * if expression has any variables its value will be 0
     * @see Node
     *
     * @param root              : constant expression to calculate
     * @return double value     : constant expression value
     */
    public double calculate(Node root){
        return calculate(root, NO_ARG);
    }

    /**
     * Use this method to calculate value of expression for each x value from [xMin, xMax] range.
     * @see Node
     * @see Point2d
     * @see java.util.List
     *
     * @param root              : expression to calculate
     * @param xMin              : start x
     * @param xMax              : finish x
     * @return List<Point2d>    : list of expression values for each x value from [xMin, xMax] range
     */
    public List<IPoint> calculate(Node root, float xMin, float xMax) {
        float xVal = xMin;

        while (xVal <= xMax) {
            points.add(new Point2d(xVal, calculate(root, xVal)));
            xVal += (float) 1 / 10;
        }
        return points;
    }

    /**
     * Recursive method that runs over expression tree and calculate expression value for xVal.
     * @see Node
     *
     * @param root              : expression to calculate
     * @param xVal              : x value
     * @return                  : expression value for xVal
     */
    public double calculate(Node root, double xVal) {
        double result = 0;

        if (root.getChildren() == null) {
            if (root.getType() == LexemeType.VARIABLE) {
                result = xVal;
            }

            if(root.getType() == LexemeType.NUMBER){
                result = ((DoubleNode) root).getValue();
            }
        } else {

            List<Node> children = root.getChildren();
            double[] childrenValues = new double[children.size()];

            for (int i = 0; i < children.size(); i++) {
                childrenValues[i] = calculate(children.get(i), xVal);
            }

            result = calculateOperation(((StringNode) root).getValue(), root.getType(), childrenValues);
        }

        return result;
        //return MathExtended.round(result);
    }

    /**
     * Do operation with its args specified by args[].
     * @see LexemeType
     * @see Node
     *
     * TODO: doc
     * @param args                  : operation args
     * @return operation(args)
     */
    private double calculateOperation(String name, LexemeType type, double[] args) {
        if(LexemeEvaluator.isBinary(type)){
            return calculateBinary(type ,args);
        }

        if(LexemeEvaluator.isUnaryMinus(type)){
            return -args[0];
        }

        if(LexemeEvaluator.isFunction(type)){
            return calculateFunction(name, args);
        }

        return 0;

    }

    /**
     * Calculate function value for arguments specified by args[].
     * @see LexemeType
     * @see Node
     *
     * @param name                  : function name
     * @param args                  : function args
     * @return function(args[0],args[1],...)
     */
    private double calculateFunction(String name, double[] args) {
        try{
            if("abs".equals(name)){
                return MathExtended.abs(args);
            }

            if("sin".equals(name)){
                return MathExtended.sin(args);
            }

            if("cos".equals(name)){
                return MathExtended.cos(args);
            }

            if("tg".equals(name)){
                return MathExtended.tg(args);
            }

            if("ctg".equals(name)){
                return MathExtended.ctg(args);
            }

            if("arsin".equals(name)){
                return MathExtended.arsin(args);
            }

            if("arcos".equals(name)){
                return MathExtended.arcos(args);
            }

            if("artg".equals(name)){
                return MathExtended.artg(args);
            }

            if("arctg".equals(name)){
                return MathExtended.arctg(args);
            }

            if("ln".equals(name)){
                return MathExtended.ln(args);
            }

            if("lg".equals(name)){
                return MathExtended.lg(args);
            }

            if("logd".equals(name)){
                return MathExtended.logd(args);
            }


            if("log2".equals(name)){
                return MathExtended.log2(args);
            }

            if("sh".equals(name)){
                return MathExtended.sh(args);
            }

            if("ch".equals(name)){
                return MathExtended.ch(args);
            }

            if("th".equals(name)){
                return MathExtended.th(args);
            }

            if("cth".equals(name)){
                return MathExtended.cth(args);
            }

            if("todeg".equals(name)){
                return MathExtended.todeg(args);
            }

            if("torad".equals(name)){
                return MathExtended.torad(args);
            }

            if("floor".equals(name)){
                return MathExtended.floor(args);
            }

            if("ceil".equals(name)){
                return MathExtended.ceil(args);
            }

            if("min".equals(name)){
               return MathExtended.findMin(args);
            }

            if("max".equals(name)){
               return MathExtended.findMax(args);
            }
        } catch (IllegalArgumentsAmountException e){
            //
        }

        return 0;
    }

    /**
     * Calculate binary operation value for arguments specified by args[].
     * @see LexemeType
     * @see Node
     *
     * @param type                  : binary operation type
     * @param args                  : binary operation args
     * @return binary_operation(args[0],args[1],...)
     */
    private double calculateBinary(LexemeType type, double[] args) {

        if (type == LexemeType.BINARY_PLUS){
            return args[0] + args[1];
        }

        if (type == LexemeType.BINARY_MINUS){
            return args[0] - args[1];
        }

        if (type == LexemeType.BINARY_MULTIPLY){
            return args[0] * args[1];
        }

        if (type == LexemeType.BINARY_DIVIDE){
            return args[0] / args[1];
        }

        if (type == LexemeType.BINARY_POWER){
            return Math.pow(args[0],args[1]);
        }

        if (type == LexemeType.BINARY_MOD){
            return args[0] % args[1];
        }

        return 0;
    }


}
