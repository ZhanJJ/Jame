package com.example.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by James on 2016/8/24.
 */
public class CustomTestView extends View {
    Paint mPaint;

    public CustomTestView(Context context) {
        super(context);
    }

    public CustomTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int radius = 10;
        int strokeWidth = 20;
        float corrected = strokeWidth / 2;
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //设置抗锯齿
        mPaint.setColor(Color.GRAY); //设置画笔颜色
        mPaint.setStyle(Paint.Style.STROKE); //设置画笔风格，实心、空心
        mPaint.setStrokeWidth(strokeWidth); //设置空心边框的宽度
//        //绘制点
//        canvas.drawPoint(getWidth() / 2, getHeight() / 2  ,  mPaint);
//        //绘制直线
//        canvas.drawLine(0, 0, getWidth() / 2, getHeight() / 2, mPaint);
//        //绘制多条直线
//        float[] pts = {
//                0, 0, getWidth() / 2, getHeight() / 2,
//                getWidth() / 2, getHeight() / 2, getWidth() / 2, 0,
//                getWidth() / 2, 0, getWidth(), 0
//        };
//        canvas.drawLines(pts, mPaint);
//        //绘制矩形
//        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
//        //绘制圆角矩形
//        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10, mPaint);
//        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
//        canvas.drawRoundRect(rectF, 10, 10, mPaint);
        //绘制弧形、扇形（区别在于倒数第二个参数userCenter，true:扇形 false：弧形）
        //初始角度：水平，向X轴正向
//        canvas.drawArc(0, 0, getWidth() - 2, getHeight() - 2, 0, 120, true, mPaint); //true:扇形
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        int[] colors = new int[]{Color.WHITE, Color.BLUE, Color.YELLOW};
        SweepGradient sweepGradient = new SweepGradient(0, 0, colors, null);
        mPaint.setShader(sweepGradient);

        canvas.drawArc(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth, 0, 360, false, mPaint); // false：弧形
        //绘制椭圆
//        canvas.drawOval(0+corrected, 0+corrected, getWidth()-corrected, getHeight() / 2-corrected, mPaint);
        //绘制文本
//        mPaint.setTextSize(DisplayUtil.sp2px(getContext(), 15)); //设置文本字体大小
//        mPaint.setStrokeWidth(1); //设置画笔宽度，控制文本字体粗细程度
//        canvas.drawText("你大爷", 0, getHeight() / 2, mPaint);
        //绘制路径
//        Path path = new Path();
//        path.moveTo(10, 10);
//        path.lineTo(100, 100);
//        path.lineTo(100, 0);
//        path.moveTo(10, 100);
//        path.lineTo(60, 10);
//        canvas.drawPath(path, mPaint);
        //画时钟
//        int mRadius = 180;
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 180, mPaint);
//        mPaint.setStrokeWidth(3);
//        for (int i = 0; i < 12; i++) {
//            if (i == 0 || i == 3 || i == 6 || i == 9) {
//                mPaint.setStrokeWidth(5);
//                canvas.drawLine(getWidth() / 2, getHeight() / 2 - mRadius, getWidth() / 2, getHeight() / 2 - mRadius + 10, mPaint);
//                mPaint.setTextSize(DisplayUtil.sp2px(getContext(), 12));
//                mPaint.setStrokeWidth(1);
//                String num = String.valueOf(i);
//                canvas.drawText(num, getWidth() / 2 - mPaint.measureText(num) / 2, getHeight() / 2 - mRadius + 45, mPaint);
//            } else {
//                canvas.drawLine(getWidth() / 2, getHeight() / 2 - mRadius, getWidth() / 2, getHeight() / 2 - mRadius + 5, mPaint);
//            }
//            canvas.rotate(30, getWidth() / 2, getHeight() / 2);
//        }
//        canvas.save();
//
//        canvas.translate(getWidth() / 2, getHeight() / 2);
//        mPaint.setStrokeWidth(10);
//        canvas.drawLine(0, 0, 30, 40, mPaint);
//        mPaint.setStrokeWidth(5);
//        canvas.drawLine(0, 0, -50, +30, mPaint);
//        canvas.restore();
    }

    public class BarAnimation extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
        }
    }

}
