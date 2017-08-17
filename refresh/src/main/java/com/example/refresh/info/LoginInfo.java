package com.example.refresh.info;

import java.util.List;

/**
 * Created by James on 2017/1/6.
 */

public class LoginInfo {

    /**
     * msg : 登录成功
     * response_time : 1483666006490
     * status : 1
     * tag : [{"tagkey":"tag1","tagvalue":"11"}]
     * token : 83e227da82da043c32c457bc8ccf1d76
     */

    private String msg;
    private long response_time;
    private String status;
    private String token;
    private List<TagBean> tag;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(long response_time) {
        this.response_time = response_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TagBean> getTag() {
        return tag;
    }

    public void setTag(List<TagBean> tag) {
        this.tag = tag;
    }

    public static class TagBean {
        /**
         * tagkey : tag1
         * tagvalue : 11
         */

        private String tagkey;
        private String tagvalue;

        public String getTagkey() {
            return tagkey;
        }

        public void setTagkey(String tagkey) {
            this.tagkey = tagkey;
        }

        public String getTagvalue() {
            return tagvalue;
        }

        public void setTagvalue(String tagvalue) {
            this.tagvalue = tagvalue;
        }
    }
}
