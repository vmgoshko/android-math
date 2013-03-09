package by.bsu.mg.math.views;

import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GraphPlaneRenderer implements Renderer {

    private final List<IPoint> points;
    private final int DIMENSIONS = 3;

    public GraphPlaneRenderer(Context context,List<IPoint> points) {
        this.points = points;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(1.f, 1.f, 1.f, 0);

        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();


        // draw quad
        gl.glTranslatef(0, 0, 0);
        gl.glColor4f(1.f,0.f,0.f,0.f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        FloatBuffer buffer = createBuffer();
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

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

    //TODO: optimize buffer creation
    private FloatBuffer createBuffer(){
        float[] bufferArray = new float[DIMENSIONS*points.size()];
        for(int i = 0; i < points.size(); i += 3){
            double[] pointCoords = points.get(i).to3dArray();
            bufferArray[i] = (float) pointCoords[0];
            bufferArray[i+1] = (float) pointCoords[1];
            bufferArray[i+2] = (float) pointCoords[2];
        }

        return FloatBuffer.wrap(bufferArray);
    }

}
