package by.bsu.mg.math.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import by.bsu.mg.math.utils.Point2d;


import java.util.List;

/**
 * Create by Vladimir Goshko goshkovm@tut.by
 */
public class GraphPlane extends View {


    private float scale = 10.f;
    private Paint paint;
    private List<Point2d> points;


    public GraphPlane(Context context, List<Point2d> points) {
        super(context);
        init(points);
    }

    private void init(List<Point2d> points) {
        this.points = points;
        paint = new Paint();
        paint.setAntiAlias(true);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scale += 0.5 * Math.signum(motionEvent.getY() - view.getHeight() / 2);
                view.invalidate();
                return true;
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setCoordinates(canvas);
        drawAxis(canvas);

        drawFunctionGraph(canvas, Color.RED);
    }


    private void setCoordinates(Canvas canvas) {
        Matrix translateMatrix = new Matrix();
        translateMatrix.postScale(scale, scale);
        translateMatrix.postTranslate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.setMatrix(translateMatrix);
    }


    private void drawAxis(Canvas canvas) {
        float width = canvas.getWidth();
        float height = canvas.getHeight();

        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawLine((-1) * width / 2, 0.f, width / 2, 0, paint);
        canvas.drawLine(0, (-1) * height / 2, 0, height / 2, paint);
    }

    private void drawFunctionGraph(Canvas canvas, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.f / scale);

        for (int i = 0; i < points.size() - 1; i++) {
            Point2d pStart = points.get(i);
            Point2d pEnd = points.get(i + 1);

            float xStart = (float) pStart.getX();
            float yStart = (float) ((-1.f) * pStart.getY());

            float xEnd = (float) pEnd.getX();
            float yEnd = (float) ((-1.f) * pEnd.getY());
            canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
        }
    }

}
