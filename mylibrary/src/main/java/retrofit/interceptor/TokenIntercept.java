package retrofit.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kin on 2017/2/13.
 */

public class TokenIntercept implements Interceptor {
    Map<String, String> mParamMap;

    public TokenIntercept(Map<String, String> paramMap) {
        this.mParamMap = paramMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        // 添加新的参数
        if (mParamMap != null && mParamMap.size() > 0) {
            for (Map.Entry<String, String> entry : mParamMap.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        //新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
