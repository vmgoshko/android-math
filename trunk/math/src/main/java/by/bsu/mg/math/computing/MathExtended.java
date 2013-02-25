package by.bsu.mg.math.computing;

import by.bsu.mg.math.exceptions.IllegalArgumentsAmoutException;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class MathExtended {
    private static String[] functionOperations = {"sin", "cos", "tg", "ctg", "arsin", "arcos",
                                                  "artg", "arctg", "ln", "log10", "log2", "logd",
                                                  "todeg", "torad", "abs", "floor", "ceil", "sinh",
            "cosh", "max", "min"};
    public static double sin(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("sin", args,1);
        return Math.sin(args[0]);
    }

    public static double cos(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("cos", args, 1);
        return Math.cos(args[0]);
    }

    public static double tg(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("tg",args,1);
        return Math.tan(args[0]);
    }

    public static double ctg(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("ctg",args,1);
        return 1/Math.tan(args[0]);
    }

    public static double arsin(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("arsin", args,1);
        return Math.asin(args[0]);
    }

    public static double arcos(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("arcos",args,1);
        return Math.acos(args[0]);
    }

    public static double artg(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("artg", args,1);
        return Math.atan(args[0]);
    }

    public static double arctg(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("arctg", args,1);
        return Math.PI/2 - Math.atan(args[0]);
    }

    public static double ln(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("ln",args,1);
        return Math.log(args[0]);
    }

    public static double lg(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("lg", args,1);
        return Math.log10(args[0]);
    }

    public static double log2(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("log2",args,1);
        return Math.log(args[0])/Math.log(2);
    }

    public static double logd(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("logd", args,2);
        return Math.log(args[0])/Math.log(args[1]);
    }

    public static double todeg(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("todeg", args,1);
        return args[0]*180/Math.PI;
    }

    public static double torad(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("torad", args,1);
        return args[0]*Math.PI/180;
    }

    public static double abs(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("abs", args,1);
        return Math.abs(args[0]);
    }

    public static double floor(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("floor",args,1);
        return Math.floor(args[0]);
    }

    public static double ceil(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("ceil",args,1);
        return Math.ceil(args[0]);
    }

    public static double sinh(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("sinh",args,1);
        return Math.sinh(args[0]);
    }

    public static double cosh(double[] args) throws IllegalArgumentsAmoutException {
        checkArgs("cosh",args,1);
        return Math.cosh(args[0]);
    }


    /**
     * Find minimum element in array
     * @param array                 : arguments
     * @return minimum element
     */
    public static double findMin(double[] array){
        double min = 0;

        for (double anArray : array) {
            if (anArray < min) {
                min = anArray;
            }
        }

        return min;
    }

    /**
     * Find maximum element in array
     * @param array                 : arguments
     * @return maximum element
     */
    public static double findMax(double[] array){
        double max = 0;

        for (double anArray : array) {
            if (anArray > max) {
                max = anArray;
            }
        }

        return max;
    }


    private static void checkArgs(String operation, double[] args, int argsAmount) throws IllegalArgumentsAmoutException {
        if(args.length != argsAmount){
            throw new IllegalArgumentsAmoutException(operation, argsAmount);
        }
    }

}
