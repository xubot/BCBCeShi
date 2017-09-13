package com.example.bckj.projectbcb.Bean.DiDiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class HuoQuYanZhengMaBeanData {
    /**
     * errorCode : 0
     * errorMessage : Success
     * result : {"cell":"*******2122","data":[],"errno":"0","error":"OK","switchOn":false}
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
         * cell : *******2122
         * data : []
         * errno : 0
         * error : OK
         * switchOn : false
         */

        private String cell;
        private String errno;
        private String error;
        private boolean switchOn;
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

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }
}
