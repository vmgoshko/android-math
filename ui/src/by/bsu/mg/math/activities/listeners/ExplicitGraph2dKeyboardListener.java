package by.bsu.mg.math.activities.listeners;

import android.support.v4.view.ViewPager;
import android.widget.TextView;
import by.bsu.mg.math.activities.graphing.Explicit2DGraphingActivity;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphPlane2D;
import by.bsu.mg.math.views.opengl.renderers.ExplicitRenderer2D;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ExplicitGraph2dKeyboardListener extends KeyboardClickListener {
    private final GLGraphPlane2D glGraphPlane;
    private final ExplicitRenderer2D renderer2D;
    private ExpressionBuilder builder = new ExpressionBuilder();
    private Explicit2DGraphingActivity explicit2DGraphingActivity;

    public ExplicitGraph2dKeyboardListener(Explicit2DGraphingActivity activity, ExpressionEditText editText, ViewPager pager, TextView outView,
                                           GLGraphPlane2D glGraphPlane, ExplicitRenderer2D renderer2D) {
        super(activity, pager, outView);
        this.currentEdit = editText;
        this.glGraphPlane = glGraphPlane;
        this.renderer2D = renderer2D;
        this.explicit2DGraphingActivity = activity;
    }

    @Override
    protected void onEqual() {
        String exprStr = currentEdit.getText().toString();

        if (exprStr.length() == 0) {
            return;
        }

        renderer2D.setExpression(builder.buildTree(exprStr));
        glGraphPlane.requestRender();
        explicit2DGraphingActivity.setGraphPlaneContent();

    }
}
