package by.bsu.mg.math.utils;

import java.util.Arrays;

/**
 * Create by Vladimir Goshko vmgoshko@gmail.com
 */
public class Point3d implements IPoint{
    private double x;
    private double y;
    private double z;

    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setCoords(double[] coords) throws IllegalArgumentException {
        if(coords.length != 3){
            throw new IllegalArgumentException(
                    String.format("Can't set coords %s to 2D point",Arrays.toString(coords))
            );
        }

        x = coords[0];
        y = coords[1];
        z = coords[2];
    }

    public double[] to2dArray(){
        double[] pointArr = new double[2];
        pointArr[0] = x;
        pointArr[1] = y;

        return pointArr;
    }

    public double[] to3dArray(){
        double[] pointArr = new double[3];
        pointArr[0] = x;
        pointArr[1] = y;
        pointArr[2] = z;

        return pointArr;
    }
}
