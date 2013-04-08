package by.bsu.mg.math.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import by.bsu.mg.math.utils.IPoint;
import by.bsu.mg.math.utils.Point2d;

import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class GLGraphPlane extends GLSurfaceView{

    private float scale = 10.f;
    private List<IPoint> points;
    private final GraphPlaneRenderer renderer;


    public GLGraphPlane(Context context, List<IPoint> points) {
        super(context);
        this.points = points;

        renderer = new GraphPlaneRenderer(context, points);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
