package com.example.refresh.okhttp;

import android.os.Environment;
import android.util.Log;

import com.example.refresh.api.AppConfig;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lzcheng3 on 2016/7/26.
 */
public class CommonOkHttp {

    private static CommonOkHttp mInstance;
    private OkHttpClient client;
    private int cacheSize = 50 * 1024 * 1024;
    private int timeout = 10000;
    private static final String TYPE = "application/octet-stream";

    // private mInstance(){}
    private CommonOkHttp() {
        // OkHttpClient client = new OkHttpClient();
        //cookie enabled
        //client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        innitOkHttp();
    }

    /**
     * 静态内部类单例模式：保线程安全也能保证CommonOkHttp类的唯一性
     *
     * @return
     */
    public static CommonOkHttp getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static CommonOkHttp sInstance = new CommonOkHttp();
    }


    private CommonOkHttp innitOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (isExistExternalStore()) {
            File cacheDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + AppConfig.OKHTTP_CACHE_PATH);
            if (!cacheDirectory.exists()) {
                cacheDirectory.mkdirs();
            }
            Cache cache = new Cache(cacheDirectory, cacheSize);
            builder.cache(cache);
            builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        }
        client = builder.build();
        return mInstance;
    }

    public boolean isExistExternalStore() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static Map<Class<?>, List<Call>> callMap;

    /**
     * 保存请求
     *
     * @param clazz
     * @param call
     */
    private static void putCall(Class<?> clazz, Call call) {
        if (null != clazz) {
            if (callMap == null) {
                callMap = new HashMap<Class<?>, List<Call>>();
            }
            if (!callMap.containsKey(clazz)) {
                List<Call> callList = new ArrayList<Call>();
                callList.add(call);
                callMap.put(clazz, callList);
            } else {
                callMap.get(clazz).add(call);
            }
        }
    }

    /**
     * 清除请求
     *
     * @param clazz
     */
    public void clearCall(Class<?> clazz) {
        List<Call> callList = callMap.get(clazz);
        //Log.e("CommonOkHttp","in clearCall");
        if (null != callList) {
            // Log.e("CommonOkHttp","null != callList");
            int i = 0;
            for (Call call : callList) {
                //Log.e("CommonOkHttp","null != callList"+ i++);
                if (!call.isCanceled())
                    call.cancel();
            }
            callMap.remove(clazz);
        }
    }


    /**
     * 异步GET请求，不带参数
     *
     * @param actionUrl
     * @param callback
     * @return 回调函数
     * @throws IOException
     */
    private Callback getAsyncHttp(String actionUrl, Callback callback) throws IOException {
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request.Builder requestBuilder = new Request.Builder().url(requestUrl);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        //创建一个请求
        Request request = requestBuilder.url(requestUrl).build();

        //创建一个Call
        Call call = client.newCall(request);
        call.enqueue(callback);

        return callback;
    }

    /**
     * 异步GET请求，不带参数
     *
     * @param actionUrl
     * @param callback
     * @return 回调函数
     * @throws IOException
     */
    private Callback getAsyncHttp(Class<?> clazz, String actionUrl, Callback callback) throws IOException {
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request.Builder requestBuilder = new Request.Builder().url(requestUrl);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        //创建一个请求
        Request request = requestBuilder.url(requestUrl).build();

        //创建一个Call
        Call call = client.newCall(request);
        call.enqueue(callback);
        putCall(clazz, call);
        return callback;
    }

    /**
     * 异步GET请求，带参数
     *
     * @param actionUrl
     * @param paramsMap
     * @param callback
     * @return 回调函数
     * @throws IOException
     */
    private Callback getAsyncHttpByMapParam(String actionUrl, HashMap<String, String> paramsMap, Callback callback) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s?%s", AppConfig.BASE_URL, actionUrl, tempParams.toString());
            Log.e("requestUrl", requestUrl);
            Request.Builder requestBuilder = new Request.Builder().url(requestUrl);
            //可以省略，默认是GET请求
            requestBuilder.method("GET", null);
            //创建一个请求
            Request request = requestBuilder.url(requestUrl).build();

            //创建一个Call
            Call call = client.newCall(request);
            //执行请求
            call.enqueue(callback);
        } catch (Exception e) {
            Log.e("Tag", e.toString());
        }
        return callback;
    }

    /**
     * 异步GET请求，带参数
     *
     * @param clazz
     * @param actionUrl
     * @param paramsMap
     * @param callback
     * @return 回调函数
     * @throws IOException
     */
    private Callback getAsyncHttpByMapParam(Class<?> clazz, String actionUrl, HashMap<String, String> paramsMap, Callback callback) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s?%s", AppConfig.BASE_URL, actionUrl, tempParams.toString());
            Log.e("requestUrl", requestUrl);
            Request.Builder requestBuilder = new Request.Builder().url(requestUrl);
            //可以省略，默认是GET请求
            requestBuilder.method("GET", null);
            //创建一个请求
            Request request = requestBuilder.url(requestUrl).build();

            //创建一个Call
            Call call = client.newCall(request);
            //执行请求
            call.enqueue(callback);
            //把请求放进map
            putCall(clazz, call);
        } catch (Exception e) {
            Log.e("Tag", e.toString());
        }
        return callback;
    }

    /**
     * 异步POST请求，带参数
     *
     * @param formBody
     * @param callback
     * @return
     * @throws IOException
     */
    private Callback postAsyncHttpByParam(RequestBody formBody, Callback callback) throws IOException {
        //补全请求地址
        String requestUrl = AppConfig.BASE_URL;

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return callback;
    }

    /**
     * 异步POST请求，带参数
     *
     * @param actionUrl
     * @param formBody
     * @param callback
     * @return
     * @throws IOException
     */
    private Callback postAsyncHttpByParam(String actionUrl, RequestBody formBody, Callback callback) throws IOException {
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return callback;
    }

    /**
     * @param actionUrl
     * @param formBody
     * @param callback
     * @return
     * @throws IOException
     */
    private Callback postAsyncHttpByParam(Class<?> clazz, String actionUrl, RequestBody formBody, Callback callback) throws IOException {


        //补全请求地址
//        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        putCall(clazz, call);
        return callback;
    }

    /**
     * 异步上传文件
     *
     * @param actionUrl
     * @param file
     * @param callBack
     * @return
     * @throws IOException
     */
    private Callback uploadFileAysnHttp(String actionUrl, File file, Callback callBack) throws IOException {
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(RequestBody.create(MediaType.parse(TYPE), file))
                .build();

        Call call = client.newCall(request);
        call.enqueue(callBack);

        return callBack;
    }

    /**
     * 异步上传文件
     *
     * @param actionUrl
     * @param file
     * @param callBack
     * @return
     * @throws IOException
     */
    private Callback uploadFileAysnHttp(Class<?> clazz, String actionUrl, File file, Callback callBack) throws IOException {
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(RequestBody.create(MediaType.parse(TYPE), file))
                .build();

        Call call = client.newCall(request);
        call.enqueue(callBack);
        putCall(clazz, call);
        return callBack;
    }

    /**
     * 异步上传文件可带参数
     *
     * @param actionUrl
     * @param
     * @param callBack
     * @return
     * @throws IOException
     */
    private Callback upLoadFileAsyHttpByParam(String actionUrl, RequestBody requestBody, Callback callBack) throws IOException {

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(requestUrl)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callBack);

        return callBack;
    }

    /**
     * 异步上传文件可带参数
     *
     * @param actionUrl
     * @param
     * @param callBack
     * @return
     * @throws IOException
     */
    private Callback upLoadFileAsyHttpByParam(Class<?> clazz, String actionUrl, RequestBody requestBody, Callback callBack) throws IOException {
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(requestUrl)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
        putCall(clazz, call);
        return callBack;
    }

    /**
     * 异步下载文件
     *
     * @param actionUrl
     * @param
     * @param callBack
     * @return
     * @throws IOException
     */
    private Callback downLoadFileAsyHttp(String actionUrl, Callback callBack) throws IOException {

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("download File url", requestUrl);
        final Request request = new Request.Builder().url(requestUrl).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);

        return callBack;
    }

    /**
     * 异步下载文件
     *
     * @param actionUrl
     * @param
     * @param callBack
     * @return
     * @throws IOException
     */
    private Callback downLoadFileAsyHttp(Class<?> clazz, String actionUrl, Callback callBack) throws IOException {

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
        //补全请求地址
        String requestUrl = String.format("%s/%s", AppConfig.BASE_URL, actionUrl);
        Log.e("requestUrl", requestUrl);
        final Request request = new Request.Builder().url(requestUrl).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
        putCall(clazz, call);
        return callBack;
    }

    //private Callback downLoadFileAsyncHttp(String actionUrl, RequestBody requestBody, Callback callBack) throws IOException

    public Callback get(String actionUrl, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().getAsyncHttp(actionUrl, callback);
    }

    public Callback get(Class clazz, String actionUrl, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().getAsyncHttp(clazz, actionUrl, callback);
    }

    public Callback get(String actionUrl, HashMap<String, String> paramsMap, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().getAsyncHttpByMapParam(actionUrl, paramsMap, callback);
    }

    public Callback get(Class clazz, String actionUrl, HashMap<String, String> paramsMap, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().getAsyncHttpByMapParam(clazz, actionUrl, paramsMap, callback);
    }

    public Callback post(RequestBody formBody, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().postAsyncHttpByParam(formBody, callback);
    }

    public Callback post(String actionUrl, RequestBody formBody, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().postAsyncHttpByParam(actionUrl, formBody, callback);
    }

    public Callback post(Class<?> clazz, String actionUrl, RequestBody formBody, Callback callback) throws IOException {
        return CommonOkHttp.getInstance().postAsyncHttpByParam(clazz, actionUrl, formBody, callback);
    }

    public Callback uploadFile(String actionUrl, File file, Callback callBack) throws IOException {
        return CommonOkHttp.getInstance().uploadFileAysnHttp(actionUrl, file, callBack);
    }

    public Callback uploadFile(Class<?> clazz, String actionUrl, File file, Callback callBack) throws IOException {
        return CommonOkHttp.getInstance().uploadFileAysnHttp(clazz, actionUrl, file, callBack);
    }

    public Callback uploadFileWithParam(String actionUrl, RequestBody requestBody, Callback callBack) throws IOException {
        return CommonOkHttp.getInstance().upLoadFileAsyHttpByParam(actionUrl, requestBody, callBack);
    }

    public Callback uploadFileWithParam(Class<?> clazz, String actionUrl, RequestBody requestBody, Callback callBack) throws IOException {
        return CommonOkHttp.getInstance().upLoadFileAsyHttpByParam(clazz, actionUrl, requestBody, callBack);
    }

    public Callback downloadFile(String actionUrl, Callback callBack) throws IOException {
        return CommonOkHttp.getInstance().downLoadFileAsyHttp(actionUrl, callBack);
    }

    public Callback downloadFile(Class<?> clazz, String actionUrl, Callback callBack) throws IOException {
        return CommonOkHttp.getInstance().downLoadFileAsyHttp(clazz, actionUrl, callBack);
    }
}
