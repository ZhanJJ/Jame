package com.example.test.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * IntentService
 */
public class LocalIntentService extends IntentService {
    private static final String TAG = "LocalIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocalIntentService(String name) {
        super(name);
    }

    public LocalIntentService() {
        super(TAG);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getStringExtra("action");
        Log.i(TAG, "receive task:" + action);
        SystemClock.sleep(2000);
        Log.i(TAG, "handler task:" + action);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "service has bean destroy");
        super.onDestroy();
    }
}
