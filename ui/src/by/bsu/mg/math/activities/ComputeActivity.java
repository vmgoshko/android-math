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
        String expr = "(sin(x)+ln(343434*x))^2+(sin(x)+ln(2*x))^2+(sin(x)+ln(2*x))^2+(sin(x)+ln(343434*x))^2+(sin(x)+ln(343434*x))^2+(sin(x)+ln(343434*x))^2+(sin(x)+ln(343434*x))^2+(sin(x)+ln(343434*x))^2";
        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        Node expression = expressionBuilder.buildTree(expr);
        XVarCalculator calc = new XVarCalculator();

        Log.d("__EXPR__", expr);
        while(true){
            timer.start();
            calc.calculate(expression, -1000, 1000);
            timer.stop();
        }
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