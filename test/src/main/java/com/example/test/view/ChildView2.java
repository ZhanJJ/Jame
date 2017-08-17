package com.example.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by James on 2016/7/14.
 */
public class ChildView2 extends View {
    public ChildView2(Context context) {
        super(context);
    }

    public ChildView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChildView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("dispatchTouch-child-2",super.dispatchTouchEvent(event)+"");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("onTouchEvent-child-2",super.onTouchEvent(event)+"");
        return super.onTouchEvent(event);
    }
}
