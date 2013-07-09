package by.bsu.mg.math.activities.computing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.activities.listeners.CalculatorKeyboardListener;
import by.bsu.mg.math.activities.listeners.KeyboardClickListener;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        pages.add(inflater.inflate(R.layout.func_keyb, null));
        pages.add(inflater.inflate(R.layout.num_keyb, null));
        pages.add(inflater.inflate(R.layout.const_keyb, null));

        KeysPagerAdapter keysPagerAdapter = new KeysPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pages);
        viewPager.setAdapter(keysPagerAdapter);
        viewPager.setCurrentItem(1);

        ExpressionEditText et = (ExpressionEditText) findViewById(R.id.firstExprEditText);
        TextView outView = (TextView) findViewById(R.id.textView);
        KeyboardClickListener keyboardClickListener = new CalculatorKeyboardListener(this, et, viewPager, outView);
        keyboardClickListener.registerListener();

        et.setHint("Expression");
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
