package by.bsu.mg.math.activities.graphing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.activities.listeners.ImplicitGraph2dKeyboardListener;
import by.bsu.mg.math.activities.listeners.KeyboardClickListener;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphPlane2D;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;
import by.bsu.mg.math.views.opengl.renderers.ImplicitRenderer2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Implicit2DGraphingActivity extends Activity {
    private GLGraphPlane2D graphPlane;
    private Timer timer = new Timer();
    private boolean isGraphing = false;
    private ImplicitRenderer2D renderer2D;

    public Implicit2DGraphingActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        graphPlane = new GLGraphPlane2D(this);
        renderer2D = new ImplicitRenderer2D();
        graphPlane.setRenderer(renderer2D);

        super.onCreate(savedInstanceState);
        setKeyboardContent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isGraphing) {
            graphPlane.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGraphing) {
            graphPlane.onResume();
        }
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

    public void setGraphing(boolean graphing) {
        isGraphing = graphing;
    }

    public ViewPager setKeyboardContent() {
        setContentView(R.layout.one_expr_graph_2d);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<>();

        pages.add(inflater.inflate(R.layout.func_keyb, null));
        pages.add(inflater.inflate(R.layout.num_keyb, null));
        View constVarKeyb = inflater.inflate(R.layout.const_vars_keyb, null);
        pages.add(constVarKeyb);

        KeysPagerAdapter keysPagerAdapter = new KeysPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pages);
        viewPager.setAdapter(keysPagerAdapter);
        viewPager.setCurrentItem(1);

        ExpressionEditText et = (ExpressionEditText) findViewById(R.id.firstExprEditText);
        TextView outView = (TextView) findViewById(R.id.textView);
        KeyboardClickListener keyboardClickListener = new ImplicitGraph2dKeyboardListener(this, et, viewPager, outView,
                graphPlane, renderer2D);

        et.setOnTouchListener(keyboardClickListener);
        keyboardClickListener.registerListener();

        et.setHint("f(x,y)=0");
        return viewPager;
    }

    public void setGraphPlaneContent() {
        this.setGraphing(true);
        setContentView(graphPlane);
    }
}

