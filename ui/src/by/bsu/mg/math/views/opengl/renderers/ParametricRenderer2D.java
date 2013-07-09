package by.bsu.mg.math.views.opengl.renderers;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.computing.calculators.TVarCalculator;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ParametricRenderer2D extends GraphPlaneRenderer2D {
    HashMap<String, Borders> borders = new HashMap<>();
    TVarCalculator tVarCalculator;
    Timer timer = new Timer();
    private Node xExpr;
    private Node yExpr;

    public ParametricRenderer2D(Node xExpr, Node yExpr, Borders tBorders) {
        this.xExpr = xExpr;
        this.yExpr = yExpr;

        drawMode = GL10.GL_LINE_STRIP;
        borders.put("t", tBorders);

        points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
    }

    public ParametricRenderer2D() {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    protected List<IPoint> calcPoints() {
        if (tVarCalculator == null)
            tVarCalculator = new TVarCalculator(borders);

        List<IPoint> xPointList = new ArrayList<>(tVarCalculator.calculate(xExpr));
        List<IPoint> yPointList = new ArrayList<>(tVarCalculator.calculate(yExpr));
        List<IPoint> result = new ArrayList<>(xPointList.size());

        for (int i = 0; i < xPointList.size(); i++) {
            double xPoint = ((Point2d) xPointList.get(i)).getY();
            double yPoint = ((Point2d) yPointList.get(i)).getY();
            result.add(new Point2d(xPoint, yPoint));
        }

        return result;
    }

    @Override
    protected void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    protected void setWidth(int width) {
        super.setWidth(width);
        tVarCalculator.setWidth(width);
    }

    public void setExpression(Node xExpr, Node yExpr, Borders tBorders) {
        this.xExpr = xExpr;
        this.yExpr = yExpr;

        drawMode = GL10.GL_LINE_STRIP;
        borders.put("t", tBorders);

        points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
    }
}
