package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LaunchTestActivity extends AppCompatActivity {
    String TAG="LaunchTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.i(TAG,"onCreate");
        Log.i("nidaye","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luanch_test);
    }

//    @Override
//    protected void onStart() {
//        Log.i(TAG,"onStart");
//        super.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        Log.i(TAG,"onResume");
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        Log.i(TAG,"onPause");
//        super.onPause();
//        finish();
//    }
//
//    @Override
//    protected void onStop() {
//        Log.i(TAG,"onStop");
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.i(TAG,"onDestroy");
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.i(TAG,"onRestart");
//        super.onRestart();
//    }
}
