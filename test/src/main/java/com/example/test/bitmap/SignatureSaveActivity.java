package com.example.test.bitmap;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignatureSaveActivity extends AppCompatActivity {
    final static String TAG = "SignatureSaveActivity";

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvContent)
    TextView tvContent;

    String title = "";
    String content = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_save);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveUserStub();
                        saveMerchantStub();
                        saveIssuingBankStub();
                    }
                }).start();
            }
        });


    }

    //保存顾客用小票
    private void saveUserStub() {
        title = "顾客用";
        SystemClock.sleep(1000);
        saveStub(title, content);
    }

    //保存商户用小票
    private void saveMerchantStub() {
        title = "商户用";
        content = "这是签名";
        SystemClock.sleep(1000);
        saveStub(title, content);
    }

    //保存发卡行用小票
    private void saveIssuingBankStub() {
        title = "发卡行用";
        content = "这是签名";
        SystemClock.sleep(1000);
        saveStub(title, content);
    }

    //保存小票
    private synchronized void saveStub(String title, String content) {
        Log.i(TAG, "title = " + title + ", content=" + content);
    }

}
