package com.example.refresh.api;

import android.content.Context;

import com.example.refresh.Utils;
import com.example.refresh.httpClient.RequestParams;
import com.example.refresh.httpClient.listener.DisposeDataHandle;
import com.example.refresh.httpClient.listener.DisposeDataListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James on 2016/12/16.
 */

public class UserApi extends BaseApi {

    private static UserApi mInstance;

    public static UserApi get() {
        if (mInstance == null) {
            mInstance = new UserApi();
        }
        return mInstance;
    }

    /**
     * 8003 用户登录
     *
     * @param context
     * @param phone    手机号码
     * @param pwd      密码
     * @param listener
     */
    public void Login(Context context, String phone, String pwd,
                      final DisposeDataHandle<String> listener) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("type", "8003");
        requestParams.put("phone", phone);
        requestParams.put("password", Utils.getMD5(pwd));
        httpPost(requestParams, new DisposeDataListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {

            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }
}
