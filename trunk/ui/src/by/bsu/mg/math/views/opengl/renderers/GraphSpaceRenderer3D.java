package by.bsu.mg.math.views.opengl.renderers;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import by.bsu.mg.math.utils.IPoint;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public abstract class GraphSpaceRenderer3D implements GLSurfaceView.Renderer {

    protected final static int SIZEOF_FLOAT = Float.SIZE / 8;
    private final static float lightAmb[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private final static float lightDif[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private final static float lightPos[] = {0.0f, 0.0f, 0.0f, 1.f};
    private static final FloatBuffer lightAmbBfr;
    private static final FloatBuffer lightDifBfr;
    private static final FloatBuffer lightPosBfr;

    static {
        lightAmbBfr = wrap(lightAmb);
        lightDifBfr = wrap(lightDif);
        lightPosBfr = wrap(lightPos);
    }

    protected List<IPoint> points;
    protected float x;
    protected float y;
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer normalBuffer;
    protected double xSize = 10;
    protected double ySize = 10;
    protected double zSize = 10;
    protected FloatBuffer colorBuffer;
    private float eyeFrom = 5;
    private FloatBuffer axisBuffer;
    private float axisSize = 10;
    private float rotateAngleLeftRight = 0;
    private float rotateAngleUpDown = 0;
    private float prevY = -1;
    private float prevX = -1;

    public GraphSpaceRenderer3D() {
    }

    protected static FloatBuffer wrap(float[] coords) {
        FloatBuffer buffer;
        ByteBuffer tempByteBuffer = ByteBuffer.allocateDirect(coords.length * SIZEOF_FLOAT);
        tempByteBuffer.order(ByteOrder.nativeOrder());
        buffer = tempByteBuffer.asFloatBuffer();
        buffer.put(coords);
        buffer.flip();

        return buffer;
    }

    protected abstract List<IPoint> calcPoints();

    protected abstract void pointsToFloatBuffer(List<IPoint> points);

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(1, 1, 1, 1);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLightModelx(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);

        gl.glClearDepthf(100000000.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);

        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbBfr);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDifBfr);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBfr);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, eyeFrom, eyeFrom, eyeFrom, 0, 0, 0, 0, 0, 1);
        gl.glRotatef(rotateAngleUpDown, -1, 1, 0);

        gl.glRotatef(rotateAngleLeftRight, 0, 0, 1);
        gl.glColor4f(1, 0, 0, 1);
        drawAxis(gl);
        if (vertexBuffer != null) {
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            //   gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            //   gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertexBuffer.capacity() / 3);
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            // gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        }
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glLightModelx(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // avoid division by zero
        if (height == 0)
            height = 1;
        // draw on the entire screen
        gl.glViewport(0, 0, width, height);
        // setup projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 1.0f, 100.0f);
    }

    public void setScale(float scale) {
        this.eyeFrom /= scale;
        this.axisSize /= scale;
    }

    private void drawAxis(GL10 gl) {
        axisBuffer = createAxisBuffer();
        FloatBuffer colorBuffer = createAxisColorBuffer();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, axisBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glDrawArrays(GL10.GL_LINES, 0, axisBuffer.capacity() / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    }

    private FloatBuffer createAxisBuffer() {
        float[] axisBufferArray = new float[2 * 3 * 3];
        // x axis
        axisBufferArray[0] = 0;
        axisBufferArray[1] = 0;
        axisBufferArray[2] = 0;

        axisBufferArray[3] = axisSize;
        axisBufferArray[4] = 0;
        axisBufferArray[5] = 0;

        // y axis
        axisBufferArray[6] = 0;
        axisBufferArray[7] = 0;
        axisBufferArray[8] = 0;

        axisBufferArray[9] = 0;
        axisBufferArray[10] = axisSize;
        axisBufferArray[11] = 0;

        // z axis
        axisBufferArray[12] = 0;
        axisBufferArray[13] = 0;
        axisBufferArray[14] = 0;

        axisBufferArray[15] = 0;
        axisBufferArray[16] = 0;
        axisBufferArray[17] = axisSize;

        return wrap(axisBufferArray);
    }

    private FloatBuffer createAxisColorBuffer() {
        float[] axisColorBufferArray = new float[2 * 3 * 4];
        for (int i = 0; i < axisColorBufferArray.length; i++) {
            axisColorBufferArray[i] = 0;
        }
        return wrap(axisColorBufferArray);
    }

    public void rotateLeft() {
        rotateAngleLeftRight -= 5;
    }

    public void rotateRight() {
        rotateAngleLeftRight += 5;
    }

    public void rotateUp() {
        rotateAngleUpDown += 5;
    }

    public void rotateDown() {
        rotateAngleUpDown -= 5;
    }
}
