package com.example.bckj.projectbcb.Bean.DiDiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class FaXiaoXiBean {
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
