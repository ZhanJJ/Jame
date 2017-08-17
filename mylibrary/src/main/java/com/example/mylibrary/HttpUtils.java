package com.example.mylibrary;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by James on 2016/10/28.
 */

public class HttpUtils {
    static String TAG = "HttpRequest Unexpected code";

    public static OkHttpClient mOkHttpClient = new OkHttpClient();

    public String httpGet(String uri) throws IOException {
        String result = "";
        Request request = new Request.Builder()
                .url(uri)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            result = response.body().string();
        } else {
            Log.i(TAG, response.code() + "");
        }
        return result;
    }

    public String httpPost() {
        String result = "";
        return result;
    }


}
