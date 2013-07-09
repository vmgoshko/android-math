package by.bsu.mg.math.activities.listeners;

import android.support.v4.view.ViewPager;
import android.widget.TextView;
import by.bsu.mg.math.activities.graphing.Implicit2DGraphingActivity;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphPlane2D;
import by.bsu.mg.math.views.opengl.renderers.ImplicitRenderer2D;

import java.util.Queue;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ImplicitGraph2dKeyboardListener extends KeyboardClickListener {
    private final GLGraphPlane2D glGraphPlane;
    private final ImplicitRenderer2D renderer2D;
    private ExpressionBuilder builder = new ExpressionBuilder();
    private Implicit2DGraphingActivity implicitGraphingActivity;

    public ImplicitGraph2dKeyboardListener(Implicit2DGraphingActivity activity, ExpressionEditText editText, ViewPager pager, TextView outView,
                                           GLGraphPlane2D glGraphPlane, ImplicitRenderer2D renderer2D) {
        super(activity, pager, outView);
        this.currentEdit = editText;
        this.glGraphPlane = glGraphPlane;
        this.renderer2D = renderer2D;
        this.implicitGraphingActivity = activity;
    }

    @Override
    protected void onEqual() {
        try {
            String exprStr = currentEdit.getText().toString();

            if (exprStr.length() == 0) {
                return;
            }

            renderer2D.setExpression(builder.buildTree(exprStr));
            implicitGraphingActivity.setGraphPlaneContent();
        } catch (RuntimeException e) {
            outView.setText(e.toString());
            Queue queue = builder.getErrors();
            e.printStackTrace();
        }
    }

}
