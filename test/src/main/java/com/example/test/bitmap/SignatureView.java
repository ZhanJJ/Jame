package com.example.test.bitmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kin on 2017/2/27.
 */

public class SignatureView extends View {
    public static final float STROKE_WIDTH = 10;
    public static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    Paint paint = new Paint();
    Path path = new Path();

    /**
     * 创建一个代表脏区域的矩形；
     * 获得 ACTION_DOWN 事件的 X 与 Y 坐标，用来设置矩形的顶点；
     * 获得 ACTION_MOVE 和 ACTION_UP 事件的新坐标点，加入到矩形中使之拓展开来（别忘了上文说过的历史坐标点）；
     * 刷新 新生成的脏区域。
     */
    RectF dirtyRect = new RectF();
    float lastTouchX;
    float lastTouchY;

    RectF allDirtyRect = new RectF();   //代表全部脏区域

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;

                initAllDirtyRect(eventX, eventY);
                // There is no end point yet, so don't waste cycles invalidating.
                return true;
            }
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                resetDirtyRect(eventX, eventY);
                updateAllDirtyRect(eventX, eventY);

                // When the hardware tracks events faster than they are delivered, the
                // event will contain a history of those skipped points.
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                // After replaying history, connect the line to the touch point.
                path.lineTo(eventX, eventY);
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;
        return true;
    }

    //扩展脏区域
    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private void resetDirtyRect(float eventX, float eventY) {
        // The lastTouchX and lastTouchY were set when the ACTION_DOWN
        // motion event occurred.
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }

    /**
     * init allDirtyRect status
     *
     * @param eventX
     * @param eventY
     */
    private void initAllDirtyRect(float eventX, float eventY) {
        if (allDirtyRect.isEmpty()) { //矩形默认坐标
            allDirtyRect.left = eventX;
            allDirtyRect.top = eventY;
            allDirtyRect.right = eventX;
            allDirtyRect.bottom = eventY;
            return;
        }
    }

    /**
     * Resets all dirty region when the motion event occurs.
     *
     * @param eventX
     * @param eventY
     */
    private void updateAllDirtyRect(float eventX, float eventY) {
        if (eventX < 0 || eventY < 0) {
            eventX = eventX < 0 ? 0 : eventX;
            eventY = eventY < 0 ? 0 : eventY;
        }
        allDirtyRect.left = Math.min(allDirtyRect.left, eventX);
        allDirtyRect.top = Math.min(allDirtyRect.top, eventY);
        allDirtyRect.right = Math.max(allDirtyRect.right, eventX);
        allDirtyRect.bottom = Math.max(allDirtyRect.bottom, eventY);
    }

    public void clean() {
        path.reset();
        allDirtyRect.setEmpty();
        invalidate();
    }

    public RectF getRect() {
        return allDirtyRect;
    }
}
