package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import by.bsu.mg.math.computing.algorithms.differentiation.NumericalDerivator;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Differentiate extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);
        NumericalDerivator differentiator = new NumericalDerivator();
        ExpressionBuilder builder = new ExpressionBuilder();

        Node root = builder.buildTree("x^2");
        ((TextView) findViewById(R.id.debugText)).setText(String.valueOf(differentiator.diff(root, 1, 3)));
    }
}