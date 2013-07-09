package by.bsu.mg.math.views.opengl.renderers;

import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.computing.algorithms.CashedMarchingSquares;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;

import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ImplicitRenderer2D extends GraphPlaneRenderer2D {
    private CashedMarchingSquares marchingSquares;
    private Node expr;

    public ImplicitRenderer2D(Node expr) {
        super();
        this.expr = expr;
        Borders xBorders = new Borders(-orthoWidth / 2, orthoWidth / 2);
        Borders yBorders = new Borders(-orthoHeight / 2, orthoHeight / 2);
        marchingSquares = new CashedMarchingSquares(expr, xBorders, yBorders, 7, 4);
        points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
    }

    public ImplicitRenderer2D() {

    }

    @Override
    protected List<IPoint> calcPoints() {
        //timer.start();
        Borders xBorders = new Borders(-orthoWidth / 2, orthoWidth / 2);
        Borders yBorders = new Borders(-orthoHeight / 2, orthoHeight / 2);
        marchingSquares.setBorders(xBorders, yBorders);

        List<IPoint> pointList = (List<IPoint>) marchingSquares.startMarch();


        //timer.stop();

        return pointList;
    }

    public void setExpression(Node expr) {
        this.scaleFactor = 1;
        this.expr = expr;
        Borders xBorders = new Borders(-orthoWidth / 2, orthoWidth / 2);
        Borders yBorders = new Borders(-orthoHeight / 2, orthoHeight / 2);
        marchingSquares = new CashedMarchingSquares(expr, xBorders, yBorders, 7, 4);
        points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
    }
}