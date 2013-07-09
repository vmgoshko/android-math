package by.bsu.mg.math.activities.listeners;

import android.support.v4.view.ViewPager;
import android.widget.TextView;
import by.bsu.mg.math.activities.graphing.Explicit3DGraphingActivity;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphSpace3D;
import by.bsu.mg.math.views.opengl.renderers.ExplicitRenderer3D;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ExplicitGraph3dKeyboardListener extends KeyboardClickListener {
    private final GLGraphSpace3D glGraphSpace3D;
    private final ExplicitRenderer3D renderer3D;
    private ExpressionBuilder builder = new ExpressionBuilder();
    private Explicit3DGraphingActivity explicitGraphingActivity;

    public ExplicitGraph3dKeyboardListener(Explicit3DGraphingActivity activity, ExpressionEditText editText, ViewPager pager, TextView outView,
                                           GLGraphSpace3D glGraphSpace3D, ExplicitRenderer3D renderer3D) {
        super(activity, pager, outView);
        this.currentEdit = editText;
        this.glGraphSpace3D = glGraphSpace3D;
        this.renderer3D = renderer3D;
        this.explicitGraphingActivity = activity;
    }

    @Override
    protected void onEqual() {
        String exprStr = currentEdit.getText().toString();

        if (exprStr.length() == 0) {
            return;
        }

        renderer3D.setExpression(builder.buildTree(exprStr));
        glGraphSpace3D.requestRender();
        explicitGraphingActivity.setGraphPlaneContent();

    }
}
