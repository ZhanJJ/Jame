package com.example.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.example.test.R;

/**
 * 自定义圆形渐变色进度条
 * Created by James on 2016/9/11.
 */
public class CustomCircleView extends View {
    private int mWidth;      //进度条宽度
    private int mPadding=2;    //进度条与背景圆环边距
    private int mBgColor;  //背景圆环颜色
    private int mFgColor;  //进度条颜色

    public CustomCircleView(Context context) {
        super(context);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView);
        mWidth = typedArray.getInt(R.styleable.CustomCircleView_width,20);
        mBgColor = typedArray.getColor(R.styleable.CustomCircleView_bg_color,Color.WHITE);
        mFgColor = typedArray.getColor(R.styleable.CustomCircleView_fg_color,Color.GRAY);
    }

}
