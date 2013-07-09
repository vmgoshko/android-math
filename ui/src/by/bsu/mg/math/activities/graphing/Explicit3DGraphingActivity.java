package by.bsu.mg.math.activities.graphing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.activities.listeners.ExplicitGraph3dKeyboardListener;
import by.bsu.mg.math.activities.listeners.KeyboardClickListener;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphSpace3D;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;
import by.bsu.mg.math.views.opengl.renderers.ExplicitRenderer3D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Explicit3DGraphingActivity extends Activity {
    private GLGraphSpace3D glGraphSpace3D;
    private Timer timer = new Timer();
    private ExplicitRenderer3D renderer3D;
    private boolean isGraphing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        glGraphSpace3D = new GLGraphSpace3D(this);
        renderer3D = new ExplicitRenderer3D();
        glGraphSpace3D.setRenderer(renderer3D);

        setKeyboardContent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glGraphSpace3D.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glGraphSpace3D.onResume();
    }

    public ViewPager setKeyboardContent() {
        setContentView(R.layout.one_expr_graph_2d);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<>();

        pages.add(inflater.inflate(R.layout.func_keyb, null));
        pages.add(inflater.inflate(R.layout.num_keyb, null));
        pages.add(inflater.inflate(R.layout.const_vars_keyb, null));

        KeysPagerAdapter keysPagerAdapter = new KeysPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pages);
        viewPager.setAdapter(keysPagerAdapter);
        viewPager.setCurrentItem(1);


        ExpressionEditText et = (ExpressionEditText) findViewById(R.id.firstExprEditText);
        TextView outView = (TextView) findViewById(R.id.textView);
        KeyboardClickListener keyboardClickListener = new ExplicitGraph3dKeyboardListener(this, et, viewPager, outView,
                glGraphSpace3D, renderer3D);

        et.setOnTouchListener(keyboardClickListener);
        keyboardClickListener.registerListener();

        et.setHint("z=f(x,y)");
        return viewPager;
    }

    public void setGraphPlaneContent() {
        this.setGraphing(true);
        setContentView(glGraphSpace3D);
    }

    public void setGraphing(boolean graphing) {
        this.isGraphing = graphing;
    }

    @Override
    public void onBackPressed() {
        if (isGraphing) {
            isGraphing = false;
            setKeyboardContent();
        } else {
            super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

}