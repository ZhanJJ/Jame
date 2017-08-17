package com.example.refresh.httpClient.client;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.refresh.httpClient.RequestParams;
import com.example.refresh.httpClient.listener.DisposeRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by James on 2016/12/15.
 */

public class HttpClient {
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("multipart/form-data; charset=utf-8");
    public static OkHttpClient mHttpClient = new OkHttpClient().newBuilder().build();


    public static void HttpPostClient() {
    }

    public static void HttpPostConnection(final String url, final RequestParams params, DisposeRequestListener listener) {
        final RequestHandler mHandler = new RequestHandler(listener);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, analyseRequestParams(params));
                int statusCode=0;
                String strResult = "";
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = mHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        statusCode  =200;
                        strResult=response.body().toString().trim();
                    }else {
                        statusCode = response.code();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = null;
                if (!TextUtils.isEmpty(strResult)){
                    try {
                        jsonObject = new JSONObject(strResult);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = new Message();
                msg.obj = jsonObject;
                msg.arg1 = statusCode;
                mHandler.sendMessage(msg);
            }
        };
        Thread thread  = new Thread(runnable);
        thread.start();
    }

     static class RequestHandler extends Handler{
         static DisposeRequestListener mRequestListener;
         public RequestHandler(DisposeRequestListener requestListener){
             this.mRequestListener  = requestListener;
         }

         @Override
         public void handleMessage(Message msg) {
             if (mRequestListener==null){
                 return;
             }
             if (msg.arg1==200){
                 JSONObject jsonObject = (JSONObject) msg.obj;
                 if (jsonObject==null){
                     mRequestListener.onFailed("数据为空，请求失败");
                     return;
                 }
                 try {
                     mRequestListener.onSuccess(jsonObject);
                 } catch (JSONException e) {
                     mRequestListener.onFailed("数据解析错误");
                 }
             }else {
                 mRequestListener.onFailed("网络请求失败");
             }
         }
     }


    public static String analyseRequestParams(RequestParams params){
        String strResult = "";
        strResult = params.toString();
        return strResult;
    }

}
