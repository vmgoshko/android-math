package by.bsu.mg.math.activities.computing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.activities.listeners.IntegrateKeyboardListener;
import by.bsu.mg.math.activities.listeners.KeyboardClickListener;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class IntegrateActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integrate_layout);

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
        ExpressionEditText upEt = (ExpressionEditText) findViewById(R.id.integrationEditTextUp);
        ExpressionEditText downEt = (ExpressionEditText) findViewById(R.id.integrationEditTextDown);

        TextView outView = (TextView) findViewById(R.id.textView);
        KeyboardClickListener keyboardClickListener = new IntegrateKeyboardListener(this, viewPager, outView, et, upEt, downEt);
        keyboardClickListener.registerListener();

        et.setOnTouchListener(keyboardClickListener);
        upEt.setOnTouchListener(keyboardClickListener);
        downEt.setOnTouchListener(keyboardClickListener);

        et.setHint("f(x)");
        upEt.setHint("b");
        downEt.setHint("a");
    }
}