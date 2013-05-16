package by.bsu.mg.math.views;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public abstract class GraphPlaneRenderer2D implements Renderer {

    protected final static int SIZEOF_FLOAT = Float.SIZE / 8;
    protected final Node expr;
    protected List<IPoint> points;
    protected FloatBuffer buffer;
    protected float x;
    protected float y;
    protected int prevTouchX = -1;
    protected int prevTouchY = -1;
    protected int touchX = -1;
    protected int touchY = -1;
    protected float orthoWidth;
    protected float orthoHeight;
    protected Point2d firstFingerPos[] = new Point2d[2];
    protected Point2d secondFingerPos[] = new Point2d[2];
    private float xFullOffset = 0;
    private float yFullOffset = 0;
    private float scaleFactor = 1;
    private Timer timer = new Timer();
    private FloatBuffer axisBuffer;

    public GraphPlaneRenderer2D(Node expr) {
        this.expr = expr;
        this.orthoWidth = 10;
        this.orthoHeight = orthoWidth;
        axisBuffer = createAxisBuffer();
    }

    private static FloatBuffer wrap(float[] coords) {
        FloatBuffer buffer;
        ByteBuffer tempByteBuffer = ByteBuffer.allocateDirect(coords.length * SIZEOF_FLOAT);
        tempByteBuffer.order(ByteOrder.nativeOrder());
        buffer = tempByteBuffer.asFloatBuffer();
        buffer.put(coords);
        buffer.flip();

        return buffer;
    }

    private FloatBuffer createAxisBuffer() {
        float bufferArray[] = new float[8];

        bufferArray[0] = x - orthoWidth / 2;
        bufferArray[1] = y;

        bufferArray[2] = x + orthoWidth / 2;
        bufferArray[3] = y;

        bufferArray[4] = x;
        bufferArray[5] = y + orthoHeight / 2;

        bufferArray[6] = x;
        bufferArray[7] = y - orthoHeight / 2;

        return wrap(bufferArray);
    }

    protected abstract List<IPoint> calcPoints();

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(255, 255, 255, 0);
        gl.glEnable(GL10.GL_BLEND);
        gl.glEnable(GL10.GL_ALPHA_BITS);
        gl.glEnable(GL10.GL_MULTISAMPLE);
        gl.glEnable(GL10.GL_SMOOTH);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_NICEST);
        gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
    }

    public void onDrawFrame(GL10 gl) {

        GLES20.glClearColor(1, 1, 1, 1);
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glScalef(scaleFactor, scaleFactor, scaleFactor);
        drawAxis(gl);

        gl.glColor4f(0.5f, 0, 0, 0);
        gl.glLineWidth(2);
        if (buffer != null) {
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer(2, GL10.GL_FLOAT, 0, buffer);
            gl.glDrawArrays(GL10.GL_LINES, 0, points.size());
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        }

    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0)
            height = 1;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        this.orthoWidth = 10 / scaleFactor;
        this.orthoHeight = orthoWidth * height / width / scaleFactor;

        GLU.gluOrtho2D(gl, -orthoWidth / 2, orthoWidth / 2, -orthoHeight / 2, orthoHeight / 2);
    }

    protected FloatBuffer pointsToFloatBuffer(List<IPoint> points) {
        if (points.size() == 0) {
            return null;
        }
        IPoint point;
        float coords[] = new float[points.size() * 2];

        point = points.get(0);
        coords[0] = (float) ((Point2d) point).getX() - x;
        coords[1] = (float) ((Point2d) point).getY() - y;

        for (int i = 1; i < points.size(); i++) {
            point = points.get(i);
            coords[2 * i] = (float) ((Point2d) point).getX() - x;
            coords[2 * i + 1] = (float) ((Point2d) point).getY() - y;
        }

        return wrap(coords);
    }

    public void setScale(float scale) {
        this.scaleFactor *= scale;

        orthoHeight /= scale;
        orthoWidth /= scale;
        this.points = calcPoints();
        buffer = pointsToFloatBuffer(this.points);
        axisBuffer = createAxisBuffer();
    }

    private void drawAxis(GL10 gl) {
        gl.glColor4f(0, 0, 0, 1);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, axisBuffer);
        gl.glDrawArrays(GL10.GL_LINES, 0, axisBuffer.capacity() / 2);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

    /*private void translate(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        prevTouchX = touchX;
        prevTouchY = touchY;

        touchX = x;
        touchY = y;

        if (prevTouchX == -1)
            prevTouchX = x;

        if (prevTouchY == -1)
            prevTouchY = y;

        float translX = orthoWidth * (touchX - prevTouchX) / view.getWidth();
        float translY = -orthoHeight * (touchY - prevTouchY) / view.getHeight();

        translateEyeXY(translX, translY);

    }

    private void translateEyeXY(float x, float y) {
        this.x += x;
        this.y += y;
        xFullOffset += x;
        yFullOffset += y;

        boolean isXRecalc = Math.abs(xFullOffset) > orthoWidth;
        boolean isYRecalc = Math.abs(yFullOffset) > orthoHeight;

        if (isXRecalc || isYRecalc) {
            this.points = calcPoints();
            buffer = pointsToFloatBuffer(this.points);
            xFullOffset = 0;
            yFullOffset = 0;
        }
    }*/

}
