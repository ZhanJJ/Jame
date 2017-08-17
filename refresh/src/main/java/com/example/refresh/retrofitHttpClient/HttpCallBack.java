package com.example.refresh.retrofitHttpClient;

import org.json.JSONObject;

/**
 * Created by Kin on 2017/2/10.
 */

public interface HttpCallBack {
    void success(JSONObject model);

    void fail(String msg);
}
