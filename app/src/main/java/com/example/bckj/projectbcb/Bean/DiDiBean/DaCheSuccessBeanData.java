package com.example.bckj.projectbcb.Bean.DiDiBean;

/**
 * Created by Administrator on 2017/9/9.
 */

public class DaCheSuccessBeanData {


    /**
     * errorCode : 0
     * errorMessage :
     * result : {"errno":103,"errmsg":"乘客被禁用"}
     */

    private String errorCode;
    private String errorMessage;
    private ResultBean result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
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
         * errno : 103
         * errmsg : 乘客被禁用
         */
        private String oid;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        private int errno;
        private String errmsg;

        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }
    }
}
