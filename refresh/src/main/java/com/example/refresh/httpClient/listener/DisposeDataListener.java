package com.example.refresh.httpClient.listener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James on 2016/12/15.
 * 请求数据回调接口
 */

public interface DisposeDataListener {
    void onSuccess(JSONObject jsonObject) throws JSONException;
    void onFailed(String msg);
}
