package com.example.refresh.api;

import com.example.refresh.httpClient.RequestParams;
import com.example.refresh.httpClient.client.HttpClient;
import com.example.refresh.httpClient.listener.DisposeDataListener;
import com.example.refresh.httpClient.listener.DisposeRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James on 2016/12/16.
 */

public class BaseApi {
    protected static String URL = "http://115.28.228.192:9090";

    public void httpPost(RequestParams params, final DisposeDataListener dataListener) {
        HttpClient.HttpPostConnection(URL, params, new DisposeRequestListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                int status = jsonObject.optInt("status",0);
                String msg = jsonObject.optString("msg");
                switch (status) {
                    case 1:{
                        dataListener.onSuccess(jsonObject);
                        break;
                    }
                    case -1:{
                        dataListener.onFailed(msg);
                        break;
                    }
                    default:
                        dataListener.onFailed(msg);
                        break;
                }
            }

            @Override
            public void onFailed(String msg) {
                dataListener.onFailed(msg);
            }
        });
    }
}
