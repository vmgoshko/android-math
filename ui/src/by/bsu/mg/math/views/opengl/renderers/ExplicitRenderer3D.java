package by.bsu.mg.math.views.opengl.renderers;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.computing.calculators.XYVarCalculator;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point3d;

import java.util.HashMap;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ExplicitRenderer3D extends GraphSpaceRenderer3D {

    private Node expression;
    private HashMap<String, Borders> borders = new HashMap<>();
    private float normals[];
    private float colors[];
    private Timer timer = new Timer();

    public ExplicitRenderer3D(Node expression) {
        this.expression = expression;
        this.points = calcPoints();
        pointsToFloatBuffer(points);
    }

    public ExplicitRenderer3D() {

    }

    @Override
    protected List<IPoint> calcPoints() {
        borders.clear();

        borders.put("x", new Borders(-xSize / 2, xSize / 2));
        borders.put("y", new Borders(-ySize / 2, ySize / 2));
        XYVarCalculator calculator = new XYVarCalculator(borders);
        return calculator.calculate(expression);
    }

    @Override
    protected void pointsToFloatBuffer(List<IPoint> points) {
        timer.start();
        int xCount = XYVarCalculator.xCount;
        int yCount = XYVarCalculator.yCount;
        float[] triangledPoints = new float[3 * 3 * 2 * (xCount - 1) * (yCount - 1)];
        normals = new float[3 * 3 * 2 * (xCount - 1) * (yCount - 1)];
        colors = new float[4 * 3 * 2 * (xCount - 1) * (yCount - 1)];

        int jOffset = (yCount - 1) * 2 * 3 * 3;
        int iOffset = 2 * 3 * 3;

        int triangleCounter = 0;
        for (int j = 0; j < xCount - 1; j++)
            for (int i = 0; i < yCount - 1; i++) {

                Point3d first = (Point3d) points.get(i + yCount * j);
                Point3d third = (Point3d) points.get(i + xCount + yCount * j);
                Point3d second = (Point3d) points.get(i + 1 + yCount * j);

                triangledPoints[iOffset * i + jOffset * j] = (float) first.getX();
                triangledPoints[iOffset * i + 1 + jOffset * j] = (float) first.getY();
                triangledPoints[iOffset * i + 2 + jOffset * j] = (float) first.getZ();
                calcNormal(triangleCounter++, first, second, third);


                triangledPoints[iOffset * i + 3 + jOffset * j] = (float) second.getX();
                triangledPoints[iOffset * i + 4 + jOffset * j] = (float) second.getY();
                triangledPoints[iOffset * i + 5 + jOffset * j] = (float) second.getZ();
                calcNormal(triangleCounter++, first, second, third);


                triangledPoints[iOffset * i + 6 + jOffset * j] = (float) third.getX();
                triangledPoints[iOffset * i + 7 + jOffset * j] = (float) third.getY();
                triangledPoints[iOffset * i + 8 + jOffset * j] = (float) third.getZ();
                calcNormal(triangleCounter++, first, second, third);


                first = (Point3d) points.get(i + 1 + xCount + yCount * j);
                second = (Point3d) points.get(i + xCount + yCount * j);
                third = (Point3d) points.get(i + 1 + yCount * j);

                triangledPoints[iOffset * i + 9 + jOffset * j] = (float) first.getX();
                triangledPoints[iOffset * i + 10 + jOffset * j] = (float) first.getY();
                triangledPoints[iOffset * i + 11 + jOffset * j] = (float) first.getZ();
                calcNormal(triangleCounter++, first, second, third);


                triangledPoints[iOffset * i + 12 + jOffset * j] = (float) second.getX();
                triangledPoints[iOffset * i + 13 + jOffset * j] = (float) second.getY();
                triangledPoints[iOffset * i + 14 + jOffset * j] = (float) second.getZ();
                calcNormal(triangleCounter++, first, second, third);


                triangledPoints[iOffset * i + 15 + jOffset * j] = (float) third.getX();
                triangledPoints[iOffset * i + 16 + jOffset * j] = (float) third.getY();
                triangledPoints[iOffset * i + 17 + jOffset * j] = (float) third.getZ();
                calcNormal(triangleCounter++, first, second, third);

            }

        calcColors();

        vertexBuffer = wrap(triangledPoints);
        normalBuffer = wrap(normals);
        colorBuffer = wrap(colors);
        timer.stop();
    }

    private void calcNormal(int i, Point3d f, Point3d s, Point3d t) {
        float x1 = (float) (s.getX() - f.getX());
        float y1 = (float) (s.getY() - f.getY());
        float z1 = (float) (s.getZ() - f.getZ());

        float x2 = (float) (t.getX() - f.getX());
        float y2 = (float) (t.getY() - f.getY());
        float z2 = (float) (t.getZ() - f.getZ());

        float normalX = (y1 * z2 - y2 * z1);
        float normalY = (x2 * z1 - x1 * z2);
        float normalZ = (x1 * y2 - x2 * y1);

        float normalLen = (float) Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);

        normalX /= normalLen;
        normalY /= normalLen;
        normalZ /= normalLen;

        normals[3 * i] = normalX;
        normals[3 * i + 1] = normalY;
        normals[3 * i + 2] = normalZ;

    }

    private void calcColors() {
        for (int i = 0; i < colors.length; i += 4) {
            colors[i] = 1;
            colors[i + 1] = 0;
            colors[i + 2] = 0;
            colors[i + 3] = 1;
        }
    }

    public void setExpression(Node expr) {
        this.expression = expr;
        points = calcPoints();
        pointsToFloatBuffer(points);
    }
}
