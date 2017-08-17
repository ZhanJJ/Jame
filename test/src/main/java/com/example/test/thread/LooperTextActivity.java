package com.example.test.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.test.R;

/**
 * Handler会把一个线程消息发送到当前线程的消息队列(MessageQueue) ,最后Handler会处理消息队列中的消息
 * 管理消息队列的就是Looper
 */
public class LooperTextActivity extends AppCompatActivity {
    private static String TAG = "LooperTextActivity";
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "主线程向子线程传递MSG";
                mHandler.sendMessage(msg);
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                // 在子线程中初始化一个Looper对象
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 把UI线程发送来的消息打印出来
                        Log.i(TAG, "what=" + msg.what + ", " + msg.obj);
                    }
                };
                // 把刚才初始化的Looper对象运行起来，循环消息队列的消息
                Looper.loop();
            }
        }).start();
    }

}
