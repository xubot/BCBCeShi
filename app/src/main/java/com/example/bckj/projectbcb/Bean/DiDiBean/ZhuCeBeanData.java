package com.example.bckj.projectbcb.Bean.DiDiBean;

/**
 * Created by Administrator on 2017/9/9.
 */

public class ZhuCeBeanData {

    /**
     * errorCode : 0
     * errorMessage : Success
     * result : {"nameValuePairs":{"userType":"0","status":"��ע��"}}
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
         * nameValuePairs : {"userType":"0","status":"��ע��"}
         */

        private NameValuePairsBean nameValuePairs;

        public NameValuePairsBean getNameValuePairs() {
            return nameValuePairs;
        }

        public void setNameValuePairs(NameValuePairsBean nameValuePairs) {
            this.nameValuePairs = nameValuePairs;
        }

        public static class NameValuePairsBean {
            /**
             * userType : 0
             * status : ��ע��
             */

            private String userType;
            private String status;

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
