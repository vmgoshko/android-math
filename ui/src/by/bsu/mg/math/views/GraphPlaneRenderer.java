package by.bsu.mg.math.views;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import by.bsu.mg.math.debug.Timer;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GraphPlaneRenderer implements Renderer {

    private final List<IPoint> points;
    private final int DIMENSIONS = 3;
    private final static int SIZEOF_FLOAT = Float.SIZE / 8;
    private float translate = 0;
    private Timer timer = new Timer();
    private FloatBuffer buffer;

    public GraphPlaneRenderer(Context context,List<IPoint> points) {
        this.points = points;
        buffer = pointsToFloatBuffer(points);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0, 0, 0, 0);

        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void onDrawFrame(GL10 gl) {
        timer.start();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // draw triangle
        gl.glTranslatef(translate, 0, -10);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, points.size());

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        timer.stop();

        translate += 0.1;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // avoid division by zero
        if (height == 0) height = 1;
        // draw on the entire screen
        gl.glViewport(0, 0, width, height);
        // setup projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 1.0f, 100.0f);
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

    private FloatBuffer line(IPoint first, IPoint second){
        float[] line = new float[6];
        float[] firstFloats = first.to3dArray();
        float[] secondFloats = second.to3dArray();

        line[0] = firstFloats[0];
        line[1] = firstFloats[1];
        line[2] = firstFloats[2];

        line[3]= secondFloats[0];
        line[4]= secondFloats[1];
        line[5]= secondFloats[2];

        return wrap(line);
    }
    private FloatBuffer pointsToFloatBuffer(List<IPoint> points){
        IPoint point;
        float coords[] = new float[points.size() * 3];

        for(int i = 0; i < points.size(); i++){
            point = points.get(i);
            coords[3 * i] = (float) ((Point2d)point).getX();
            coords[3 * i + 1] = (float) ((Point2d)point).getY();
            coords[3 * i + 2] = 0;
        }

        return wrap(coords);
    }

}
