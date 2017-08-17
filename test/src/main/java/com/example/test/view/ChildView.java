package com.example.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by James on 2016/7/14.
 */
public class ChildView extends View {
    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChildView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.i("ChildView dispatchTouch",super.dispatchTouchEvent(event)+"");
//        return super.dispatchTouchEvent(event);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("onTouchEvent-child-1",super.onTouchEvent(event)+"");
//        return super.onTouchEvent(event);
//    }
}
