package by.bsu.mg.math.views.opengl.renderers;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.computing.calculators.XVarCalculator;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;

import javax.microedition.khronos.opengles.GL10;
import java.util.HashMap;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ExplicitRenderer2D extends GraphPlaneRenderer2D {

    private HashMap<String, Borders> borders = new HashMap<>();
    private XVarCalculator xVarCalculator;
    private Borders xBorders = new Borders(-orthoWidth / 2, orthoWidth / 2);
    private Timer timer = new Timer();
    private Node expr;

    public ExplicitRenderer2D(Node expr) {
        super();
        this.expr = expr;

        drawMode = GL10.GL_LINE_STRIP;
        borders.put("x", xBorders);

        points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
    }

    public ExplicitRenderer2D() {
        xBorders = new Borders(-orthoWidth / 2, orthoWidth / 2);
        borders.remove("x");
        borders.put("x", xBorders);

        drawMode = GL10.GL_LINE_STRIP;
    }

    @Override
    protected List<IPoint> calcPoints() {
        xBorders = new Borders(-orthoWidth / 2, orthoWidth / 2);
        borders.remove("x");
        borders.put("x", xBorders);

        if (xVarCalculator == null)
            xVarCalculator = new XVarCalculator(borders);

        List<IPoint> pointList = xVarCalculator.calculate(expr);
        return pointList;
    }

    @Override
    protected void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    protected void setWidth(int width) {
        super.setWidth(width);
        if (xVarCalculator != null)
            xVarCalculator.setWidth(width);
    }

    public void setExpression(Node expr) {
        this.expr = expr;
        points = calcPoints();
        buffer = pointsToFloatBuffer(points);
    }
}