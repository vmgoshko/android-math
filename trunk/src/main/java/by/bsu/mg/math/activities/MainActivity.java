package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import by.bsu.mg.math.R;
import by.bsu.mg.math.computing.Calculator;
import by.bsu.mg.math.computing.Differentiator;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.ExpressionOptimizer;
import by.bsu.mg.math.parsing.expressions.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.views.GLGraphPlane;

import java.util.List;

public class MainActivity extends Activity {

    private GLGraphPlane graphPlane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ExpressionBuilder exprBuilder = new ExpressionBuilder();
        ExpressionOptimizer optimizer = new ExpressionOptimizer();

        Node expr = exprBuilder.buildTree("sin(x)");
        optimizer.optimize(expr);

        Calculator calc = new Calculator();
        List<IPoint> points = calc.calculate(expr, -10, 10);

        // setContentView(new GLGraphPlane(this,points));
        setContentView(R.layout.calculator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  graphPlane.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  graphPlane.onResume();
    }
}
