package com.example.test.animator;

import android.view.View;

/**
 * 使用包装类，给某个属性增加get、set方法
 * 这里自定义了width属性
 * Created by Kin on 2017/8/10.
 */

public class WrapperView {
    private View mTarget;
    public WrapperView(View view){
        this.mTarget = view;
    }

    public  int getWidth(){
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width){
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
