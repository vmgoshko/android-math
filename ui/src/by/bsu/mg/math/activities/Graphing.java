package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import by.bsu.mg.math.computing.Calculator;
import by.bsu.mg.math.computing.calculators.OneVarCalculator;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.views.GLGraphPlane;

import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Graphing extends Activity {
    private GLGraphPlane graphPlane;
    private Timer timer = new Timer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        Node expression = expressionBuilder.buildTree("sin(x)");
        OneVarCalculator calc = new OneVarCalculator();

        timer.start();
        List<IPoint> points = calc.calculate(expression, -100, 100);
        timer.stop();

        graphPlane = new GLGraphPlane(this, points);

        super.onCreate(savedInstanceState);
        setContentView(graphPlane);
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
