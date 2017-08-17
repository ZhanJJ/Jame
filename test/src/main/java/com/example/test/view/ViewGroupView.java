package com.example.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by James on 2016/7/14.
 */
public class ViewGroupView extends LinearLayout {
    public ViewGroupView(Context context) {
        super(context);
    }

    public ViewGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i("dispatchTouch-group",super.dispatchTouchEvent(ev)+"");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("onTouchEvent-group",super.onTouchEvent(event)+"");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
        return super.onInterceptTouchEvent(ev);
    }
}
