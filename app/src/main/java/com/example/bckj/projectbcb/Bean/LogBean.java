package com.example.bckj.projectbcb.Bean;

/**
 * Created by Administrator on 2017/8/7.
 */

public class LogBean {

    /**
     * code : -1
     * msg : 已经登录
     * msg_en : has logined
     * data : {"token":"d9168cc08e1adc6ef2a0af4e349910c1"}
     */

    private int code;
    private String msg;
    private String msg_en;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg_en() {
        return msg_en;
    }

    public void setMsg_en(String msg_en) {
        this.msg_en = msg_en;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public  class DataBean {
        /**
         * token : d9168cc08e1adc6ef2a0af4e349910c1
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
