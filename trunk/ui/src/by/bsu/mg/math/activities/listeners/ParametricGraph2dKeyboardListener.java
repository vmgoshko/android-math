package by.bsu.mg.math.activities.listeners;

import android.support.v4.view.ViewPager;
import by.bsu.mg.math.activities.graphing.Parametric2DGraphingActivity;
import by.bsu.mg.math.utils.Borders;
import by.bsu.mg.math.computing.calculators.OneVarCalculator;
import by.bsu.mg.math.computing.calculators.XVarCalculator;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphPlane2D;
import by.bsu.mg.math.views.opengl.renderers.ParametricRenderer2D;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ParametricGraph2dKeyboardListener extends KeyboardClickListener {

    ExpressionEditText firstExprEt;
    ExpressionEditText secondExprEt;
    ExpressionEditText tFromEt;
    ExpressionEditText tToEt;
    private ParametricRenderer2D renderer2D;
    private ExpressionBuilder builder = new ExpressionBuilder();
    private Parametric2DGraphingActivity parametric2DGraphingActivity;

    public ParametricGraph2dKeyboardListener(Parametric2DGraphingActivity parametric2DGraphingActivity, ViewPager viewPager,
                                             GLGraphPlane2D graphPlane, ParametricRenderer2D renderer2D,
                                             ExpressionEditText firstExprEt, ExpressionEditText secondExprEt,
                                             ExpressionEditText tFromEt, ExpressionEditText tToEt) {
        super(parametric2DGraphingActivity, viewPager, null);
        this.renderer2D = renderer2D;
        this.parametric2DGraphingActivity = parametric2DGraphingActivity;
        this.firstExprEt = firstExprEt;
        this.secondExprEt = secondExprEt;
        this.tFromEt = tFromEt;
        this.tToEt = tToEt;
    }

    @Override
    protected void onEqual() {
        String xExprStr = firstExprEt.getText().toString();
        String yExprStr = secondExprEt.getText().toString();
        String tFromStr = tFromEt.getText().toString();
        String tToStr = tToEt.getText().toString();

        if (xExprStr.length() == 0 || yExprStr.length() == 0 ||
                tFromStr.length() == 0 || tToStr.length() == 0) {
            return;
        }

        Node tFromExpr = builder.buildTree(tFromStr);
        Node tToExpr = builder.buildTree(tToStr);
        XVarCalculator calculator = new XVarCalculator();

        double tFrom = calculator.calculate(tFromExpr, OneVarCalculator.NO_ARG);
        double tTo = calculator.calculate(tToExpr, OneVarCalculator.NO_ARG);

        renderer2D.setExpression(builder.buildTree(xExprStr), builder.buildTree(yExprStr), new Borders(tFrom, tTo));
        parametric2DGraphingActivity.setGraphPlaneContent();

    }
}
