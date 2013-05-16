package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import by.bsu.mg.math.computing.Borders;
import by.bsu.mg.math.computing.algorithms.CashedMarchingSquares;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.views.GLGraphPlane;

import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GraphingActivity extends Activity {
    private GLGraphPlane graphPlane;
    private Timer timer = new Timer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        //Node expression = expressionBuilder.buildTree("(x^2+y^2)^2-2*3*(x^2-y^2)");
        //Node expression = expressionBuilder.buildTree("(sin(x)^2+cos(y)^2)^2-2*(sin(x)^2-cos(y)^2)");
        Node expression = expressionBuilder.buildTree("x^2+y^2-8");
        //Node expression = expressionBuilder.buildTree("y-sin(x)");
        //Node expression = expressionBuilder.buildTree("(x^2+y^2+2*0.5*x)^2-4*0.25*(x^2+y^2)");
        //Node expression = expressionBuilder.buildTree("y-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
        //Node emptyExpression = expressionBuilder.buildTree("1");
        //Node expression = expressionBuilder.buildTree("y-sin(1/x)");
        List<IPoint> points;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        graphPlane = new GLGraphPlane(this, expression);
        super.onCreate(savedInstanceState);
        setContentView(graphPlane);
        timer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        graphPlane.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        graphPlane.onResume();
    }
}
