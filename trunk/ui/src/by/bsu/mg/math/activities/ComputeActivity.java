package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import by.bsu.mg.math.computing.calculators.XVarCalculator;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.views.GLGraphPlane;

/**
 *
 */
public class ComputeActivity extends Activity {
    private GLGraphPlane graphPlane;
    private Timer timer = new Timer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
      /*  ExpressionBuilder builder = new ExpressionBuilder();
        XVarCalculator calculator = new XVarCalculator();
        Node root = builder.buildTree("cos(x)+ln(x)");

        long currMillis = System.currentTimeMillis();
        for (int i = 0; i < 5_000_000; i++) {
            calculator.calculate(root, Math.sin(i));
        }

        double time = (System.currentTimeMillis() - currMillis) / (double) 1000;
        Log.d("__TIMER__", String.valueOf(time));
        */
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