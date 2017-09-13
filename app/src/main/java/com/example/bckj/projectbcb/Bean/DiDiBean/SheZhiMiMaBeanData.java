package com.example.bckj.projectbcb.Bean.DiDiBean;

/**
 * Created by Administrator on 2017/9/9.
 */

public class SheZhiMiMaBeanData {

    /**
     * errorCode : 0
     * errorMessage : Success
     * result : {"data":[],"errno":"-414","error":"手机号或密码错误","switchOn":false}
     */

    private int errorCode;
    private String errorMessage;
    private String result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
