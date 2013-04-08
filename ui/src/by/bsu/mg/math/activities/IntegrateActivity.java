package by.bsu.mg.math.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import by.bsu.mg.math.computing.algorithms.integration.DefiniteIntegrator;
import by.bsu.mg.math.computing.algorithms.integration.SimpsonMethod;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.ExpressionBuilder;
import by.bsu.mg.math.parsing.expressions.nodes.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class IntegrateActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integrate_layout);
        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        Node root = expressionBuilder.buildTree("ln(x)");
        DefiniteIntegrator integrator = new SimpsonMethod(1,2,root);


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

        TextView textView = (TextView) findViewById(R.id.textView);
        Timer timer = new Timer();
        timer.start();
        textView.setText(String.valueOf(integrator.integrate()));
        timer.stop();
    }
}