package com.example.test.animator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.test.R;


public class ObjectAnimatorActivity extends AppCompatActivity {
    Boolean isTranslation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final WrapperView wv = new WrapperView(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action",null).show();
                  //1.基础X坐标位置偏移
//                if (isTranslation) {
//                    isTranslation = true;
//                    ObjectAnimator oa =  ObjectAnimator.ofFloat(fab,"translationX",300);
//                    oa.setDuration(300);
//                    oa.start();
//                }else {
//                    isTranslation = false;
//                    ObjectAnimator oa =  ObjectAnimator.ofFloat(fab,"translationX",-300);
//                    oa.setDuration(300);
//                    oa.start();
//                }
                //2.使用包装类来实现某个属性
//                ObjectAnimator.ofInt(wv, "width", 200).setDuration(500).start();
//                PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX",-300f);
//                PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleX",1f,0,1f);
                PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("rotation",1f,0,1f);
                ObjectAnimator.ofPropertyValuesHolder(fab,pvh3)
                        .setDuration(1000)
                        .start();
            }
        });
    }
}
