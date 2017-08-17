package com.example.refresh.interf;

import com.example.refresh.info.LoginInfo;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by James on 2017/1/6.
 */

public interface YQService {
    @FormUrlEncoded
    @POST(".")
    Call<LoginInfo> loginIn(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(".")
    Call<LoginInfo> loginIn(@Url String url, @FieldMap Map<String, String> stringMap);

    @FormUrlEncoded
    @POST("api")
    Call<LoginInfo> loginIn(@Field("type") String type, @Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST(".")
    Call<JSONObject> loginInToJson(@FieldMap Map<String, String> map);

    //设置header
    @FormUrlEncoded
    @POST(".")
    Call<JSONObject> loginInToJson(@Header("Content-type") String header, @FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(".")
    Call<String> loginInToString(@Header("Content-type") String header, @FieldMap Map<String, String> map);

    //下载图片
    @GET
    Call<ResponseBody> downloadPicture(@Url String fileUrl);

    //配合Rxjava
    @FormUrlEncoded
    @POST(".")
    Observable<JSONObject> loginInWithRxJava(@FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST(".")
    Observable<JSONObject> personalInfo(@Field("type") String type, @Field("token") String token);
}
