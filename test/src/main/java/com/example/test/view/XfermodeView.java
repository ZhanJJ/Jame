package com.example.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.test.R;

/**
 * Created by James on 2016/8/24.
 */
public class XfermodeView extends View {
    Bitmap mBgBitmap, mFgBitmap;
    Paint mPaint;
    Canvas mCanvas;
    Path mPath;


    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        mPaint.setAlpha(0);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPath = new Path();

        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.personal_bg);
        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mFgBitmap);
        mCanvas.drawColor(Color.GRAY);
//        Paint paint = new Paint();
//        paint.setShader(new LinearGradient(0, 0, mBgBitmap.getWidth()/4, mBgBitmap.getHeight()/4, Color.BLUE, Color.YELLOW, Shader.TileMode.MIRROR));
//        mCanvas.drawRect(0, 0, mBgBitmap.getWidth(), mBgBitmap.getHeight(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                mPath.lineTo(event.getX(), event.getY());
                break;
            }
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        canvas.drawBitmap(mFgBitmap, 0, 0, null);
    }
}
