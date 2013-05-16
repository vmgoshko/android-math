package by.bsu.mg.math.views;

import by.bsu.mg.math.computing.algorithms.CashedMarchingSquares;
import by.bsu.mg.math.computing.Borders;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ImplicitRenderer2D extends GraphPlaneRenderer2D {
    CashedMarchingSquares marchingSquares;
    Timer timer = new Timer();

    public ImplicitRenderer2D(Node expr) {
        super(expr);
        Borders xBorders = new Borders(-orthoWidth/2,   orthoWidth/2);
        Borders yBorders = new Borders(-orthoHeight/2,  orthoHeight/2);
        marchingSquares = new CashedMarchingSquares(expr, xBorders, yBorders, 8, 4);
        points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
    }

    @Override
    protected List<IPoint> calcPoints() {
        //timer.start();
        Borders xBorders = new Borders(-orthoWidth/2,   orthoWidth/2);
        Borders yBorders = new Borders(-orthoHeight/2,  orthoHeight/2);
        marchingSquares.setBorders(xBorders, yBorders);

        List<IPoint> pointList = (List<IPoint>) marchingSquares.startMarch();
       /*
        pointList.add(new Point2d(-orthoWidth/2,orthoHeight/2));
        pointList.add(new Point2d(orthoWidth/2,orthoHeight/2));
        pointList.add(new Point2d(orthoWidth/2,orthoHeight/2));
        pointList.add(new Point2d(orthoWidth/2,-orthoHeight/2));

        pointList.add(new Point2d(orthoWidth/2,-orthoHeight/2));
        pointList.add(new Point2d(-orthoWidth/2,-orthoHeight/2));
        pointList.add(new Point2d(-orthoWidth/2,-orthoHeight/2));
        pointList.add(new Point2d(-orthoWidth/2,orthoHeight/2));             */

        //timer.stop();

        return pointList;
    }
}