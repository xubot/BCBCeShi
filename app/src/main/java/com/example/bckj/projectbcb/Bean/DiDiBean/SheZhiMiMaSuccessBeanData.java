package com.example.bckj.projectbcb.Bean.DiDiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class SheZhiMiMaSuccessBeanData {

    /**
     * data : []
     * errno : -414
     * error : 手机号或密码错误
     * switchOn : false
     */

    private String errno;
    private String error;
    private boolean switchOn;
    private List<?> data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSwitchOn() {
        return switchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        this.switchOn = switchOn;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
