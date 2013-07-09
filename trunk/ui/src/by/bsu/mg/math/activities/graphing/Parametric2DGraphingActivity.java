package by.bsu.mg.math.activities.graphing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.activities.listeners.KeyboardClickListener;
import by.bsu.mg.math.activities.listeners.ParametricGraph2dKeyboardListener;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.opengl.views.GLGraphPlane2D;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;
import by.bsu.mg.math.views.opengl.renderers.ParametricRenderer2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Parametric2DGraphingActivity extends Activity {
    private GLGraphPlane2D graphPlane;
    private Timer timer = new Timer();
    private ParametricRenderer2D renderer2D;
    private boolean isGraphing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        graphPlane = new GLGraphPlane2D(this);
        renderer2D = new ParametricRenderer2D();
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

    @Override
    public void onBackPressed() {
        if (isGraphing) {
            isGraphing = false;
            setKeyboardContent();
        } else {
            super.onBackPressed();
        }
    }

    public void setGraphing(boolean graphing) {
        isGraphing = graphing;
    }

    public ViewPager setKeyboardContent() {
        setContentView(R.layout.two_expr_graph_2d);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<>();

        pages.add(inflater.inflate(R.layout.func_keyb, null));
        pages.add(inflater.inflate(R.layout.num_keyb, null));
        pages.add(inflater.inflate(R.layout.const_vars_keyb, null));

        KeysPagerAdapter keysPagerAdapter = new KeysPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pages);
        viewPager.setAdapter(keysPagerAdapter);
        viewPager.setCurrentItem(1);

        ExpressionEditText firstExprEt = (ExpressionEditText) findViewById(R.id.firstExprEditText);
        ExpressionEditText secondExprEt = (ExpressionEditText) findViewById(R.id.secondExprEditText);
        ExpressionEditText tFromEt = (ExpressionEditText) findViewById(R.id.tEditTextFrom);
        ExpressionEditText tToEt = (ExpressionEditText) findViewById(R.id.tEditTextTo);

        KeyboardClickListener keyboardClickListener =
                new ParametricGraph2dKeyboardListener(this, viewPager, graphPlane, renderer2D,
                        firstExprEt, secondExprEt, tFromEt, tToEt);
        firstExprEt.setOnTouchListener(keyboardClickListener);
        secondExprEt.setOnTouchListener(keyboardClickListener);
        tFromEt.setOnTouchListener(keyboardClickListener);
        tToEt.setOnTouchListener(keyboardClickListener);

        keyboardClickListener.registerListener();
        return viewPager;
    }

    public void setGraphPlaneContent() {
        this.setGraphing(true);
        setContentView(graphPlane);
    }
}