package com.example.james.jame;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.james.jame.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {


    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.tvInputLayout1)
    TextInputLayout mTvInputLayout1;
    @BindView(R.id.tvInputLayout2)
    TextInputLayout mTvInputLayout2;
    @BindView(R.id.edt1)
    EditText mEdt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbar.setTitle("这是测试");

        mTvInputLayout1.setHint("please input");
        mTvInputLayout2.setHint("please input");

        mEdt1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        &&event.getAction() == KeyEvent.ACTION_DOWN){
                    String str1 = mTvInputLayout1.getEditText().getText().toString();
                    if (str1.length()<3){
                        mTvInputLayout1.setError("not a valid text!");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }
}
