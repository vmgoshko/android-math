package by.bsu.mg.math.utils;

import java.util.Arrays;

/**
 * Create by Vladimir Goshko vmgoshko@gmail.com
 */
public class Point2d implements IPoint{
    private double x;
    private double y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
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

    public void setCoords(double[] coords) throws IllegalArgumentException {
        if(coords.length != 2){
            throw new IllegalArgumentException(
                    String.format("Can't set coords %s to 2D point",Arrays.toString(coords))
            );
        }

        x = coords[0];
        y = coords[1];
    }

    public float[] to2dArray(){
        float[] pointArr = new float[2];
        pointArr[0] = (float) x;
        pointArr[1] = (float) y;

        return pointArr;
    }

    public float[] to3dArray(){
        float[] pointArr = new float[3];
        pointArr[0] = (float) x;
        pointArr[1] = (float) y;
        pointArr[2] = 0;

        return pointArr;
    }
}