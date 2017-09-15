package com.example.bckj.projectbcb.Bean.DiDiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class FaXiaoXiBeanData {

    /**
     * errorCode : 0
     * errorMessage : Success
     * result : {"code":0,"response":{"body":{"mids":[13],"time":1505367342003},"dataversion":"1.1","errmsg":"","errno":0,"product":257}}
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
         * code : 0
         * response : {"body":{"mids":[13],"time":1505367342003},"dataversion":"1.1","errmsg":"","errno":0,"product":257}
         */

        private int code;
        private ResponseBean response;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public static class ResponseBean {
            /**
             * body : {"mids":[13],"time":1505367342003}
             * dataversion : 1.1
             * errmsg :
             * errno : 0
             * product : 257
             */

            private BodyBean body;
            private String dataversion;
            private String errmsg;
            private int errno;
            private int product;

            public BodyBean getBody() {
                return body;
            }

            public void setBody(BodyBean body) {
                this.body = body;
            }

            public String getDataversion() {
                return dataversion;
            }

            public void setDataversion(String dataversion) {
                this.dataversion = dataversion;
            }

            public String getErrmsg() {
                return errmsg;
            }

            public void setErrmsg(String errmsg) {
                this.errmsg = errmsg;
            }

            public int getErrno() {
                return errno;
            }

            public void setErrno(int errno) {
                this.errno = errno;
            }

            public int getProduct() {
                return product;
            }

            public void setProduct(int product) {
                this.product = product;
            }

            public static class BodyBean {
                /**
                 * mids : [13]
                 * time : 1505367342003
                 */

                private long time;
                private List<Integer> mids;

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public List<Integer> getMids() {
                    return mids;
                }

                public void setMids(List<Integer> mids) {
                    this.mids = mids;
                }
            }
        }
    }
}
