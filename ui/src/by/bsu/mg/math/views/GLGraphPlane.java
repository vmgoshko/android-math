package by.bsu.mg.math.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import by.bsu.mg.math.parsing.expressions.nodes.Node;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GLGraphPlane extends GLSurfaceView implements View.OnTouchListener {

    private final GraphPlaneRenderer2D renderer;
    ScaleGestureDetector mScaleDetector;
    private float scale = 10.f;

    public GLGraphPlane(Context context, Node expr) {
        super(context);

        setEGLContextClientVersion(2);
        //setEGLConfigChooser(new MultisampleConfigChooser());

        renderer = new ImplicitRenderer2D(expr);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setOnTouchListener(this);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(mScaleDetector.onTouchEvent(motionEvent))
        {
            this.requestRender();
            return true;
        }
        return false;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            renderer.setScale(detector.getScaleFactor());
            return true;
        }
    }
}
