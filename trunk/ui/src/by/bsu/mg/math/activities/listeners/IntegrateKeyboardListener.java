package by.bsu.mg.math.activities.listeners;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import by.bsu.mg.math.computing.algorithms.integration.DefiniteIntegrator;
import by.bsu.mg.math.computing.algorithms.integration.SimpsonMethod;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.views.custom.ExpressionEditText;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class IntegrateKeyboardListener extends KeyboardClickListener {
    private final ExpressionEditText exprEt;
    private final ExpressionEditText upEt;
    private final ExpressionEditText downEt;

    public IntegrateKeyboardListener(Activity activity, ViewPager pager, TextView outView,
                                     ExpressionEditText expr, ExpressionEditText up, ExpressionEditText down) {
        super(activity, pager, outView);
        this.currentEdit = expr;
        this.exprEt = expr;
        this.upEt = up;
        this.downEt = down;
    }

    @Override
    protected void onEqual() {
        String exprStr = exprEt.getText().toString();
        String upStr = upEt.getText().toString();
        String downStr = downEt.getText().toString();

        if (exprStr.length() == 0) {
            return;
        }

        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        Node root = expressionBuilder.buildTree(exprStr);
        DefiniteIntegrator integrator = new SimpsonMethod(Double.valueOf(downStr), Double.valueOf(upStr), root);
        double result = integrator.integrate();
        outView.setText(String.valueOf(result).replaceAll("Infinity", "∞"));
    }
}
