package by.bsu.mg.math.computing.calculators;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class MathExtended {
    public static double findMin(double[] childrenValues) {
        double min = childrenValues[0];

        for (int i = 1; i < childrenValues.length; i++) {
            if (childrenValues[i] < min) {
                min = childrenValues[i];
            }
        }
        return min;
    }

    public static double findMax(double[] childrenValues) {
        double max = childrenValues[0];

        for (int i = 1; i < childrenValues.length; i++) {
            if (childrenValues[i] > max) {
                max = childrenValues[i];
            }
        }
        return max;
    }
}
