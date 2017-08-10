package com.example.bckj.projectbcb.Bean;

/**
 * Created by Administrator on 2017/8/7.
 */

public class LoginBean {

    /**
     * code : 1
     * msg : 邮箱验证后登录！
     * msg_en : complete email verification before login
     * data : {"token":"a039db3af7fe878ea07beeb1ec322ce4"}
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

    public class DataBean {
        /**
         * token : a039db3af7fe878ea07beeb1ec322ce4
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
