package com.example.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.test.R;

/**
 * Created by James on 2016/8/16.
 * 圆形/圆角控件
 */
public class CircleImageView extends View {
    private String TAG = "CircleImageView";

    private int mType; //类型
    private static final int TYPE_CIRCLE = 0; //圆形
    private static final int TYPE_ROUND = 1; //圆角
    private static final int TYPE_ROUND_TOP = 2; //顶部圆角

    private int mRadius; //圆角的弧度
    private int DEFAULT_RADIUS = 20; //默认弧度
    private Bitmap mSrc; //图片资源
    private int mWidth; //宽度
    private int mHeight; //高度


    public CircleImageView(Context context) {
//        super(context); //调用父类构造方法
        this(context,null); //调用本类构造方法
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        int count = typedArray.getIndexCount();
        Log.i(TAG, "typeCount=" + count);
        for (int i = 0; i < count; i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.CircleImageView_circle_radius: {
                    mRadius = (int) typedArray.getDimension(R.styleable.CircleImageView_circle_radius, dp2px(context,DEFAULT_RADIUS));
                    Log.i(TAG, "mRadius=" + mRadius);
                    break;
                }
                case R.styleable.CircleImageView_circle_src: {
                    mSrc = BitmapFactory.decodeResource(context.getResources(), typedArray.getResourceId(R.styleable.CircleImageView_circle_src, 0));
                    Log.i(TAG, "mSrc");
                    break;
                }
                case R.styleable.CircleImageView_circle_type:{
                    mType = typedArray.getInt(i,0);
                    break;
                }
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else {
            mWidth = mSrc.getWidth() + getPaddingLeft() + getPaddingRight();
            if (widthSpecMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(mWidth, widthSpecSize);
            }
        }

        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        } else {
            mHeight = mSrc.getHeight() + getPaddingTop() + getPaddingBottom();
            if (heightSpecMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(mHeight, heightSpecSize);
            }
        }
        Log.i(TAG, "mWidth=" + mWidth);
        Log.i(TAG, "height=" + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (mSrc== null) {
            super.onDraw(canvas);
        }
        switch (mType){
            case TYPE_CIRCLE:{
                int diameter = Math.min(mWidth,mHeight);
                canvas.drawBitmap(drawCircleImage(mSrc,diameter),getPaddingLeft(),getPaddingTop(),null);
                break;
            }
            case TYPE_ROUND:{
                canvas.drawBitmap(drawRoundCornerImage(mSrc),getPaddingLeft(),getPaddingTop(),null);
                break;
            }
            case TYPE_ROUND_TOP:{
                canvas.drawBitmap(drawTopRoundCornerImage(mSrc),getPaddingLeft(),getPaddingTop(),null);
            }
        }

    }

    /**
     * 画圆角
     * @param src
     * @return
     */
    private Bitmap drawRoundCornerImage(Bitmap src) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //开启抗锯齿
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);

        src = Bitmap.createScaledBitmap(src, mWidth, mHeight, false);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        return target;
    }

    /**
     * 画顶部圆角
     * @param src
     * @return
     */
    private Bitmap drawTopRoundCornerImage(Bitmap src){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawRoundRect(new RectF(0,0,mWidth,mRadius*2),mRadius,mRadius,paint);
        canvas.drawRect(new RectF(0,mRadius,mWidth,mHeight),paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src,0,0,paint);
        return target;
    }

    /**
     * 画圆形
     * @param src
     * @param diameter
     * @return
     */
    private Bitmap drawCircleImage(Bitmap src,int diameter){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(diameter,diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(diameter/2,diameter/2,diameter/2,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src,0,0,paint);
        return target;
    }

    protected int dp2px(Context context,float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
