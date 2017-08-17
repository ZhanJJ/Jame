package com.example.refresh.interf;

import com.example.refresh.info.TngouInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by James on 2017/1/3.
 */

public interface TngouService {
    @GET("/api/{category}/list")//网址下面的子目录   category表示分类，因为子目录只有一点不一样
    Call<TngouInfo> ListRepos(@Path("category") String path, @Query("id") int id,
                              @Query("page") int page, @Query("rows") int rows);

    @GET("/api/{category}/list")
    Call<TngouInfo> getList(@Path("category") String path,@Query("id") int id,
                            @Query("page") int page,@Query("rows") int rows);

    @GET("/api/{category}/list")
    Call<TngouInfo> getList(@Path("category") String path, @QueryMap Map<String,String> map);
}
