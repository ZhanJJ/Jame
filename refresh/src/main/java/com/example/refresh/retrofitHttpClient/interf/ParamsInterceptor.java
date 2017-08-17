package com.example.refresh.retrofitHttpClient.interf;

import java.util.Map;

/**
 * Created by Kin on 2017/2/11.
 */

public interface ParamsInterceptor {
    Map checkParams(Map<String,String> map);
}
