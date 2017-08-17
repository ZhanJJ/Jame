package retrofit.utils;

import android.content.Context;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit.cacahe.CacheProvide;
import retrofit.interceptor.CacheInterceptor;
import retrofit.interceptor.DownLoadInterceptor;
import retrofit.interceptor.HttpLoggingInterceptor;
import retrofit.interceptor.RetryAndChangeIpInterceptor;

/**
 * Created by 耿 on 2016/8/27.
 */
public class OkHttpProvider {
    static OkHttpClient okHttpClient;

    public static OkHttpClient okHttpClient(final Context context, String BASE_URL, List<String> SERVERS) {
        if (okHttpClient == null) {
            synchronized (OkHttpProvider.class) {
                if (okHttpClient == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new DownLoadInterceptor(BASE_URL))
                            .addInterceptor(new RetryAndChangeIpInterceptor(BASE_URL, SERVERS))
                            .addNetworkInterceptor(new CacheInterceptor())
                            .cache(new CacheProvide(context).provideCache())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(8, TimeUnit.SECONDS)
                            .writeTimeout(8, TimeUnit.SECONDS)
                            .build();
                    if (BuildConfig.DEBUG) {//printf logs while  debug
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        client = client.newBuilder().addInterceptor(logging).build();
                    }
                    okHttpClient = client;
                }

            }

        }
        return okHttpClient;
    }
}
