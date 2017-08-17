package com.example.refresh.retrofitHttpClient;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by James on 2017/2/9.
 */

public interface RetrofitHttpService {
    @GET()
    Call<JSONObject> get(@Url String url, @FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST()
    Call<JSONObject> post(@FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST()
    Call<JSONObject> post(@Url String url, @FieldMap Map<String, String> paramMap);

    //RxJava注解
    @GET()
    Observable<JSONObject> obGet(@Url String url, @FieldMap Map<String, String> paramMap);

    //RxJava注解
    @FormUrlEncoded
    @POST()
    Observable<JSONObject> obPost(@Url String url, @FieldMap Map<String, String> paramMap);
}
