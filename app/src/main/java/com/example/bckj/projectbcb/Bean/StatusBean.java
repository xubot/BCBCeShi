package com.example.bckj.projectbcb.Bean;

/**
 * Created by Administrator on 2017/8/3.
 */

public class StatusBean {

    /**
     * code : 1
     * msg : 成功
     * msg_en : success
     * data : 1
     */

    private int code;
    private String msg;
    private String msg_en;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
