package com.example.test;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by Kin on 2017/6/30.
 */

public class JameApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext() {
        return mContext;
    }
}
