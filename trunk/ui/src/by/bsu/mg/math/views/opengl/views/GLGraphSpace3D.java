package by.bsu.mg.math.views.opengl.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import by.bsu.mg.math.views.opengl.renderers.GraphSpaceRenderer3D;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GLGraphSpace3D extends GLSurfaceView implements View.OnTouchListener {

    ScaleGestureDetector mScaleDetector;
    private GraphSpaceRenderer3D renderer;
    private float scale = 10.f;
    private float prevY = -1;
    private float prevX = -1;

    public GLGraphSpace3D(Context context) {
        super(context);
        setOnTouchListener(this);
        setOnTouchListener(this);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            prevX = -1;
            prevY = -1;
            return true;
        }

        float currX = motionEvent.getX();
        float currY = motionEvent.getY();

        if (prevX == -1)
            prevX = currX;

        if (prevY == -1)
            prevY = currY;

        if (prevX - currX < -5) {
            renderer.rotateLeft();
        }
        if (prevX - currX > 5) {
            renderer.rotateRight();
        }

        if (prevY - currY < -5) {
            renderer.rotateUp();
        }

        if (prevY - currY > 5) {
            renderer.rotateDown();
        }

        prevX = currX;
        prevY = currY;

        if (mScaleDetector.onTouchEvent(motionEvent)) {
            this.requestRender();
            return true;
        }
        this.requestRender();
        return false;
    }

    public void setRenderer(GraphSpaceRenderer3D renderer) {
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
