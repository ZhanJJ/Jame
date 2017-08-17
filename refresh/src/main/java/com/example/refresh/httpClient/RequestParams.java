package com.example.refresh.httpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 2016/12/15.
 */

public class RequestParams {
    public Map<String, String> mMap = new HashMap<>();

    public RequestParams() {

    }

    public RequestParams(Map<String, String> source) {
        if (source != null && source.size() > 0) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.mMap.put(key, value);
        }
    }
}
