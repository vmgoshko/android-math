package by.bsu.mg.math.utils;

import java.util.Arrays;

/**
 * Create by Vladimir Goshko vmgoshko@gmail.com
 */
public class Point2d implements IPoint, Comparable<Point2d> {
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


    public void setCoords(double x, double y){
        this.x = x;
        this.y = y;
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

    @Override
    public boolean equals(Object o) {
        Point2d point2d = (Point2d) o;
        return Double.compare(point2d.x, x) == 0 && Double.compare(point2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;

        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        //result = (int)(x * 10000000) + (int)(y*1000);
        return result;
    }

    @Override
    public int compareTo(Point2d point2d) {
        if(this.equals(point2d))
            return 0;

        if(x < point2d.x)
            return -1;
        else
            return y < point2d.y ? -1 : 1;

    }
}
