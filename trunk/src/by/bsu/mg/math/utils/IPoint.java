package by.bsu.mg.math.utils;

/**
 * Create by Vladimir Goshko vmgoshko@gmail.com
 */
public interface IPoint {
    public void setCoords(double[] coords);

    //TODO: projection
    public double[] to2dArray();
    //TODO: projection
    public double[] to3dArray();
}
