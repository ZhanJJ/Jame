package com.example.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.test.R;
import com.example.test.thread.LocalIntentService;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ThreadTestActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.button5)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.button5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Snackbar.make(view, "Show", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LocalIntentService.class);
                intent.putExtra("action", "Task1");
                startService(intent);
                intent.putExtra("action", "Task2");
                startService(intent);
                intent.putExtra("action", "Task3");
                startService(intent);
                break;
            case R.id.button5:
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        Log.i("run", "run1 stop!");
                    }
                };
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        Log.i("run", "run2 stop!");
                    }
                };
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        Log.i("run", "run3 stop!");
                    }
                };

                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
//                fixedThreadPool.execute(runnable);
//                fixedThreadPool.execute(runnable1);
//                fixedThreadPool.execute(runnable2);

                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//                cachedThreadPool.execute(runnable);
//                cachedThreadPool.execute(runnable1);
//                cachedThreadPool.execute(runnable2);

                ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
//                scheduledThreadPool.execute(runnable);

                ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
//                singleThreadPool.execute(runnable);
//                singleThreadPool.execute(runnable1);
//                singleThreadPool.execute(runnable2);

                int cup_count = Runtime.getRuntime().availableProcessors();
                int core_pool_size = cup_count + 1;
                int maximum_pool_size = core_pool_size * 2;
                long keep_alive = 1;

                BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(128);
                ThreadPoolExecutor threadPool = new ThreadPoolExecutor(core_pool_size, maximum_pool_size, keep_alive, TimeUnit.SECONDS, blockingDeque);

                break;
        }
    }
}
