package by.bsu.mg.math.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import by.bsu.mg.math.activities.computing.CalculatorActivity;
import by.bsu.mg.math.activities.computing.IntegrateActivity;
import by.bsu.mg.math.activities.graphing.Explicit2DGraphingActivity;
import by.bsu.mg.math.activities.graphing.Explicit3DGraphingActivity;
import by.bsu.mg.math.activities.graphing.Implicit2DGraphingActivity;
import by.bsu.mg.math.activities.graphing.Parametric2DGraphingActivity;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class MainMenu extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.menu);
        findViewById(R.id.startCalculatorBtn).setOnClickListener(this);
        findViewById(R.id.startImplicitGraphBtn).setOnClickListener(this);
        findViewById(R.id.startExplicitGraphBtn).setOnClickListener(this);
        findViewById(R.id.startParametricGraphBtn).setOnClickListener(this);
        findViewById(R.id.startIntegrationBtn).setOnClickListener(this);
        findViewById(R.id.startExplicit3Dbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        int id = view.getId();
        switch (id) {
            case R.id.startCalculatorBtn:
                intent = new Intent(this, CalculatorActivity.class);
                break;
            case R.id.startImplicitGraphBtn:
                intent = new Intent(this, Implicit2DGraphingActivity.class);
                break;
            case R.id.startExplicitGraphBtn:
                intent = new Intent(this, Explicit2DGraphingActivity.class);
                break;
            case R.id.startParametricGraphBtn:
                intent = new Intent(this, Parametric2DGraphingActivity.class);
                break;
            case R.id.startIntegrationBtn:
                intent = new Intent(this, IntegrateActivity.class);
                break;
            case R.id.startExplicit3Dbtn:
                intent = new Intent(this, Explicit3DGraphingActivity.class);
                break;
        }

        if (intent != null)
            startActivity(intent);
    }
}