package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import by.bsu.mg.math.views.GLGraphPlane;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private GLGraphPlane graphPlane;

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

        ExpressionEditText et = (ExpressionEditText) findViewById(R.id.editText);
        TextView outView = (TextView)findViewById(R.id.textView);
        KeyboardClickListener keyboardClickListener = new KeyboardClickListener(this, et, viewPager, outView);
        keyboardClickListener.registerListener();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //  graphPlane.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  graphPlane.onResume();
    }
}
