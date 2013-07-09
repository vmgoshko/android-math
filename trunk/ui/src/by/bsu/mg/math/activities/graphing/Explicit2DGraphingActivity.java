package by.bsu.mg.math.activities.graphing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.activities.listeners.ExplicitGraph2dKeyboardListener;
import by.bsu.mg.math.activities.listeners.KeyboardClickListener;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphPlane2D;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;
import by.bsu.mg.math.views.opengl.renderers.ExplicitRenderer2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Explicit2DGraphingActivity extends Activity {
    private GLGraphPlane2D graphPlane;
    private Timer timer = new Timer();
    private ExplicitRenderer2D renderer2D;
    private boolean isGraphing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        graphPlane = new GLGraphPlane2D(this);
        renderer2D = new ExplicitRenderer2D();
        graphPlane.setRenderer(renderer2D);

        setKeyboardContent();
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
        KeyboardClickListener keyboardClickListener = new ExplicitGraph2dKeyboardListener(this, et, viewPager, outView,
                graphPlane, renderer2D);

        et.setOnTouchListener(keyboardClickListener);
        keyboardClickListener.registerListener();

        et.setHint("y=y(x)");
        return viewPager;
    }

    public void setGraphPlaneContent() {
        this.setGraphing(true);
        setContentView(graphPlane);
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
