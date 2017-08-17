package com.example.james.jame;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.james.jame.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    Boolean flag = true;

    @BindView(R.id.viewTest)
    View viewTest;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnTest)
    Button btnTest;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tvRect)
    TextView tvRect;
    @BindView(R.id.tvCircle)
    TextView tvCircle;

    @OnClick(R.id.btnTest)
    void submit() {
//        Toast.makeText(this, "BindKnife", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
        Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        if (flag) {
            viewTest.animate().translationZ(100);
            flag = false;
        } else {
            viewTest.animate().translationZ(0);
            flag = true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(MainActivity.this, "BindKnife", Toast.LENGTH_SHORT).show();

            }
        });

        ViewOutlineProvider outlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
            }
        };

        ViewOutlineProvider outlineProvider1 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };

        tvRect.setOutlineProvider(outlineProvider);
//        tvCircle.setOutlineProvider(outlineProvider1);

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        Log.i("getMemoryClass", manager.getMemoryClass() + "");
    }

    @OnClick(R.id.viewTest)
    public void onClick() {
        Snackbar.make(viewTest,"This is a Snackbar",Snackbar.LENGTH_SHORT).
                setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"This is a Toast",Toast.LENGTH_SHORT).show();
                    }
                }).show();
        View view = MainActivity.this.getWindow().getDecorView();
    }
}
