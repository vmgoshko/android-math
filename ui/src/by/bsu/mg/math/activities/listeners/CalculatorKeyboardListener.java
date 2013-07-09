package by.bsu.mg.math.activities.listeners;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import by.bsu.mg.math.computing.calculators.XVarCalculator;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.views.custom.ExpressionEditText;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class CalculatorKeyboardListener extends KeyboardClickListener {
    public CalculatorKeyboardListener(Activity activity, ExpressionEditText editText, ViewPager pager, TextView outView) {
        super(activity, pager, outView);
        this.currentEdit = editText;
    }

    @Override
    protected void onEqual() {
        String exprStr = currentEdit.getText().toString();

        if (exprStr.length() == 0) {
            return;
        }

        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        Node root = expressionBuilder.buildTree(exprStr);
        XVarCalculator calculator = new XVarCalculator();
        double result = calculator.calculateNoArg(root);
        outView.setText(String.valueOf(result).replaceAll("Infinity", "∞"));
    }
}
