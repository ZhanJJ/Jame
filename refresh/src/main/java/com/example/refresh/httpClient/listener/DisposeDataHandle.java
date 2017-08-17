package com.example.refresh.httpClient.listener;

/**
 * Created by James on 2016/12/15.
 * 代码请求回调接口,响应解析类
 */

public interface DisposeDataHandle<T> {
    void onSuccess(String msg,T data);
    void onFailed(String msg);
}
