package by.bsu.mg.math.views.opengl.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import by.bsu.mg.math.views.opengl.renderers.GraphPlaneRenderer2D;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GLGraphPlane2D extends GLSurfaceView implements View.OnTouchListener {

    private GraphPlaneRenderer2D renderer;
    ScaleGestureDetector mScaleDetector;
    private float scale = 10.f;

    public GLGraphPlane2D(Context context) {
        super(context);
        setOnTouchListener(this);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mScaleDetector.onTouchEvent(motionEvent)) {
            this.requestRender();
            return true;
        }
        return false;
    }

    public void setRenderer(GraphPlaneRenderer2D renderer) {
        this.renderer = renderer;
        super.setRenderer(this.renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            renderer.setScale(detector.getScaleFactor());
            return true;
        }
    }


}
