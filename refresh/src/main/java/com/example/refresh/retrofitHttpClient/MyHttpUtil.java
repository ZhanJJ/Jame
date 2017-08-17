package com.example.refresh.retrofitHttpClient;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.refresh.custom.JsonConverterFactory;
import com.example.refresh.retrofitHttpClient.interf.ParamsInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by James on 2017/2/9.
 */

public class MyHttpUtil {
    private static volatile MyHttpUtil mInstance;
    private static volatile RetrofitHttpService mService;
    private static volatile Map<String, String> mParamMap;  //统一参数管理map
    private ParamsInterceptor mParamsInterceptor; //追加参数回调接口
    private Context mAppContext;
    private static String mVersion;

    //私有化构造方法
    private MyHttpUtil(RetrofitHttpService service, Context context, ParamsInterceptor interceptor, String strVersion) {
        this.mService = service;
        this.mAppContext = context;
        this.mParamsInterceptor = interceptor;
        this.mVersion = strVersion;
    }

    //SingleBuilder（建造者模式）
    //静态内部类
    public static class SingleBuilder {
        private Context appContext;
        private String baseUrl;
        private String version;
        private ParamsInterceptor paramsInterceptor;
        private List<String> servers = new ArrayList<>();
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        private OkHttpClient client;

        public SingleBuilder(Context context) {
            this.appContext = context.getApplicationContext();
        }

        public SingleBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public SingleBuilder version(String version) {
            this.version = version;
            return this;
        }

        public SingleBuilder paramsInterceptor(ParamsInterceptor interceptor) {
            this.paramsInterceptor = interceptor;
            return this;
        }

        public SingleBuilder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public SingleBuilder addServerUrl(String ipUrl) {
            this.servers.add(ipUrl);
            return this;
        }

        public SingleBuilder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add(factory);
            return this;
        }

        public SingleBuilder addCallFactory(CallAdapter.Factory factory) {
            this.adapterFactories.add(factory);
            return this;
        }

        public MyHttpUtil build() {
            if (converterFactories.size() == 0) {
                converterFactories.add(JsonConverterFactory.create());
            }

            if (adapterFactories.size() == 0) {
                adapterFactories.add(RxJava2CallAdapterFactory.create());
            }

            if (client == null) {
                OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                builder.readTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS);
                client = builder.build();
            }

            Retrofit.Builder builder = new Retrofit.Builder();

            for (Converter.Factory converterFactory : converterFactories) {
                builder.addConverterFactory(converterFactory);
            }

            for (CallAdapter.Factory adapterFactory : adapterFactories) {
                builder.addCallAdapterFactory(adapterFactory);
            }

            Retrofit retrofit = builder
                    .baseUrl(baseUrl)
                    .client(client)
                    .build();

            RetrofitHttpService retrofitHttpService = retrofit.create(RetrofitHttpService.class);

            mInstance = new MyHttpUtil(retrofitHttpService, appContext, paramsInterceptor, version);
            return mInstance;
        }
    }

    public static class Builder {
        Map<String, String> params = new HashMap<>();
        String url;
        String cacheTime;
        Object tag;
        boolean isAddVersion = false;

        public Builder() {
            this.setParams();
        }

        public Builder(String url) {
            this.setParams(url);
        }

        public Builder Url(String url) {
            this.url = url;
            return this;
        }

        public Builder CacheTime(String cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder Tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder AddVersion() {
            this.isAddVersion = true;
            return this;
        }

        public Builder Params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder Params(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        private void setParams() {
            this.setParams(null);
        }

        private void setParams(String url) {
            if (mInstance == null) {
                throw new NullPointerException("MyHttpUtil has not be initialized");
            }

            this.url = url;
            this.params = new HashMap<>();
        }

        private String checkUrl(String url) {
            if (TextUtils.isEmpty(url)) {
//                throw new NullPointerException("absolute url can not be empty");
                Log.i("HttpUrl", "absolute url can not be empty");
            }
            return url;
        }

        public void get() {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            Call<JSONObject> call = mInstance.mService.get(url, params);
            putCall(tag, url, call);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {

                }
            });
        }

        public void post(final HttpCallBack httpCallBack) {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            Call<JSONObject> call;
            if (TextUtils.isEmpty(this.url)) {
                call = mService.post(params);
            } else {
                call = mService.post(url, params);
            }
//            Call<JSONObject> call = TextUtils.isEmpty(this.url) ? mService.post(params) : mInstance.mService.post(url, params);
            putCall(tag, url, call);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.code() == 200) {
                        httpCallBack.success(response.body());
                    } else {
                        httpCallBack.fail(response.message());
                    }
                    removeCall(url);
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    httpCallBack.fail(t.getMessage());
                    removeCall(url);
                }
            });
        }

        public Observable<JSONObject> obPost() {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            return mInstance.mService.obPost(url, params);
        }
    }

    //检查参数
    public static Map<String, String> checkParams(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (mInstance.mParamsInterceptor != null) {
            map = mInstance.mParamsInterceptor.checkParams(map); //通过回调追加统一参数，登录信息token、设备号等
        }


        //retrofit的params的值不能为null，此处做下校验，防止出错
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                map.put(entry.getKey(), "");
            }
        }
        return map;
    }

    final static Map<String, Call> CallMap = new HashMap<>();

    //添加某个请求
    private static synchronized void putCall(Object tag, String url, Call call) {
        if (tag == null) {
            return;
        }
        synchronized (CallMap) {
            CallMap.put(tag.toString() + url, call);
        }
    }

    //移除某个请求
    private static synchronized void removeCall(String url) {
        synchronized (CallMap) {
            for (String key : CallMap.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            CallMap.remove(url);
        }
    }

    //取消某个界面的所有请求，或者是取消某个tag的所有请求
    //如果要取消某个tag单独请求，tag需要转入tag+url
    public static synchronized void cancelCall(Object tag) {
        if (tag == null) {
            return;
        }
        List<String> list = new ArrayList<>();
        synchronized (CallMap) {
            for (String key : CallMap.keySet()) {
                if (key.startsWith(tag.toString())) {
                    CallMap.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }
    }
}
