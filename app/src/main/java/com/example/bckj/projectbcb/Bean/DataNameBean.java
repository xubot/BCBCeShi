package com.example.bckj.projectbcb.Bean;

/**
 * Created by Administrator on 2017/8/7.
 */

public class DataNameBean {

    /**
     * code : 1
     * msg : 成功
     * msg_en : success
     * data : {"uid":"16","username":"QHQ","last_login_time":"1501902202","email":"1013280946@qq.com","mobile":"13521106013","status":"2","headpic":"http://localhost/sc/Uploads/Picture/default.png"}
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

    public static class DataBean {
        /**
         * uid : 16
         * username : QHQ
         * last_login_time : 1501902202
         * email : 1013280946@qq.com
         * mobile : 13521106013
         * status : 2
         * headpic : http://localhost/sc/Uploads/Picture/default.png
         */

        private String uid;
        private String username;
        private String last_login_time;
        private String email;
        private String mobile;
        private String status;
        private String headpic;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }
    }
}
