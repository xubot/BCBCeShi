package com.example.bckj.projectbcb.Bean.DiDiBean;

/**
 * Created by Administrator on 2017/8/28.
 */

public class DiDiZhuCeBean {
    /**
     * taskId : 67877e51-9cf2-47f0-a404-3e7e7a3f4192
     * status : done
     * returnJSONStr : {"nameValuePairs":{"City":"天津市","Address":"海河外滩公园附近"}}
     * errMsg :
     * spec : {"pkgName":"com.sdu.didi.psnger","versionName":"5.1.4","methodName":"getRecommendOrigin","argsJSONStr":"{\"from_lat\":\"39.01608\",\"from_lng\":\"117.66326\"}"}
     */

    private String taskId;
    private String status;
    private String returnJSONStr;
    private String errMsg;
    private SpecBean spec;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnJSONStr() {
        return returnJSONStr;
    }

    public void setReturnJSONStr(String returnJSONStr) {
        this.returnJSONStr = returnJSONStr;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public SpecBean getSpec() {
        return spec;
    }

    public void setSpec(SpecBean spec) {
        this.spec = spec;
    }

    public static class SpecBean {
        /**
         * pkgName : com.sdu.didi.psnger
         * versionName : 5.1.4
         * methodName : getRecommendOrigin
         * argsJSONStr : {"from_lat":"39.01608","from_lng":"117.66326"}
         */

        private String pkgName;
        private String versionName;
        private String methodName;
        private String argsJSONStr;

        public String getPkgName() {
            return pkgName;
        }

        public void setPkgName(String pkgName) {
            this.pkgName = pkgName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getArgsJSONStr() {
            return argsJSONStr;
        }

        public void setArgsJSONStr(String argsJSONStr) {
            this.argsJSONStr = argsJSONStr;
        }
    }
}
