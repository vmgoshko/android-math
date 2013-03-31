package by.bsu.mg.math.computing;

import by.bsu.mg.math.exceptions.IllegalArgumentsAmountException;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class MathExtended {

    private static double accuracy = 10;

    public static double sin(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("sin", args,1);
        return Math.sin(args[0]);
    }

    public static double cos(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("cos", args, 1);
        return Math.cos(args[0]);
    }

    public static double tg(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("tg",args,1);
        return Math.tan(args[0]);
    }

    public static double ctg(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("ctg",args,1);
        return 1/Math.tan(args[0]);
    }

    public static double arsin(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("arsin", args,1);
        return  Math.asin(args[0]);
    }

    public static double arcos(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("arcos",args,1);
        return Math.acos(args[0]);
    }

    public static double artg(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("artg", args,1);
        return Math.atan(args[0]);
    }

    public static double arctg(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("arctg", args,1);
        return Math.PI/2 - Math.atan(args[0]);
    }

    public static double ln(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("ln",args,1);
        return Math.log(args[0]);
    }

    public static double lg(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("lg", args,1);
        return Math.log10(args[0]);
    }

    public static double log2(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("log2",args,1);
        return Math.log(args[0])/Math.log(2);
    }

    public static double logd(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("logd", args,2);
        return Math.log(args[1])/Math.log(args[0]);
    }

    public static double todeg(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("todeg", args,1);
        return args[0]*180/Math.PI;
    }

    public static double torad(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("torad", args,1);
        return args[0]*Math.PI/180;
    }

    public static double abs(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("abs", args,1);
        return Math.abs(args[0]);
    }

    public static double floor(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("floor",args,1);
        return Math.floor(args[0]);
    }

    public static double ceil(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("ceil",args,1);
        return Math.ceil(args[0]);
    }

    public static double sh(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("sh",args,1);
        return Math.sinh(args[0]);
    }

    public static double ch(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("ch",args,1);
        return Math.cosh(args[0]);
    }

    public static double th(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("th",args,1);
        return Math.tanh(args[0]);
    }

    public static double cth(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("cth",args,1);
        return 1 / Math.tanh(args[0]);
    }

    public static double sign(double[] args) throws IllegalArgumentsAmountException {
        checkArgs("sign",args,1);
        return Math.signum(args[0]);
    }

    /**
     * Find minimum element in array
     * @param array                 : arguments
     * @return minimum element
     */
    public static double findMin(double[] array){
        double min = array[0];

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
        double max = array[0];

        for (double anArray : array) {
            if (anArray > max) {
                max = anArray;
            }
        }

        return max;
    }

    public static double round(double d){
        int precise = (int) Math.pow(10,accuracy);
        d = d * precise;
        int i = (int) Math.round(d);
        return (double) i / precise;
    }

    private static void checkArgs(String operation, double[] args, int argsAmount) throws IllegalArgumentsAmountException {
        if(args.length != argsAmount){
            throw new IllegalArgumentsAmountException(operation, argsAmount);
        }
    }


}
