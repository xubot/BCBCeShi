package com.example.bckj.projectbcb.Bean.DiDiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class DengLuBeanData {

    /**
     * errorCode : 0
     * errorMessage : Success
     * result : {"cell":"13051672112","data":[],"errno":"0","error":"OK","switchOn":false,"ticket":"rhPXQknkzGpyGmpSOM91v7x7RQys8LTw1wKeJY0oM35UjTsOwkAMRK-Cpnbh8a5x8G3CnyooK0QRcXdMSWc9z7zZMCMBwRFpU4umHhqcoofgjHQVXJDbDs95jPeyFqNrP5h2NsFYXuupAvoRXP9ftxKzfNyHkVYb9yJGr-vxa3wDAAD__w==","uid":"283730570718747"}
     */

    private int errorCode;
    private String errorMessage;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * cell : 13051672112
         * data : []
         * errno : 0
         * error : OK
         * switchOn : false
         * ticket : rhPXQknkzGpyGmpSOM91v7x7RQys8LTw1wKeJY0oM35UjTsOwkAMRK-Cpnbh8a5x8G3CnyooK0QRcXdMSWc9z7zZMCMBwRFpU4umHhqcoofgjHQVXJDbDs95jPeyFqNrP5h2NsFYXuupAvoRXP9ftxKzfNyHkVYb9yJGr-vxa3wDAAD__w==
         * uid : 283730570718747
         */

        private String cell;
        private String errno;
        private String error;
        private boolean switchOn;
        private String ticket;
        private String uid;
        private List<?> data;

        public String getCell() {
            return cell;
        }

        public void setCell(String cell) {
            this.cell = cell;
        }

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

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }
}
