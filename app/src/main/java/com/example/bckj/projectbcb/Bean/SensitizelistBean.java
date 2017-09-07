package com.example.bckj.projectbcb.Bean;

/**
 * Created by Administrator on 2017/8/29.
 */

public class SensitizelistBean {

    /**
     * code : 1
     * msg : 成功
     * msg_en : success
     * data : {"mobile":"","email":"1013280946@qq.com","list":{"TAXI":{"name":"TAXI","register_field":"mobile,password,mobile_code","login_field":"mobile,password","api_register_uri":"","api_login_uri":"","active":"1","info":{"uid":"10","taxi":"1","taxiType":"2","taxiAccount":"13521106013","taxiTime":"1501747322","taxiPass":"111111"}}}}
     */

    private int code;
    private String msg;
    private String msg_en;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mobile :
         * email : 1013280946@qq.com
         * list : {"TAXI":{"name":"TAXI","register_field":"mobile,password,mobile_code","login_field":"mobile,password","api_register_uri":"","api_login_uri":"","active":"1","info":{"uid":"10","taxi":"1","taxiType":"2","taxiAccount":"13521106013","taxiTime":"1501747322","taxiPass":"111111"}}}
         */

        private String mobile;
        private String email;
        private ListBean list;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * TAXI : {"name":"TAXI","register_field":"mobile,password,mobile_code","login_field":"mobile,password","api_register_uri":"","api_login_uri":"","active":"1","info":{"uid":"10","taxi":"1","taxiType":"2","taxiAccount":"13521106013","taxiTime":"1501747322","taxiPass":"111111"}}
             */

            private TAXIBean TAXI;

            public TAXIBean getTAXI() {
                return TAXI;
            }

            public void setTAXI(TAXIBean TAXI) {
                this.TAXI = TAXI;
            }

            public static class TAXIBean {
                /**
                 * name : TAXI
                 * register_field : mobile,password,mobile_code
                 * login_field : mobile,password
                 * api_register_uri :
                 * api_login_uri :
                 * active : 1
                 * info : {"uid":"10","taxi":"1","taxiType":"2","taxiAccount":"13521106013","taxiTime":"1501747322","taxiPass":"111111"}
                 */

                private String name;
                private String register_field;
                private String login_field;
                private String api_register_uri;
                private String api_login_uri;
                private String active;
                private InfoBean info;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRegister_field() {
                    return register_field;
                }

                public void setRegister_field(String register_field) {
                    this.register_field = register_field;
                }

                public String getLogin_field() {
                    return login_field;
                }

                public void setLogin_field(String login_field) {
                    this.login_field = login_field;
                }

                public String getApi_register_uri() {
                    return api_register_uri;
                }

                public void setApi_register_uri(String api_register_uri) {
                    this.api_register_uri = api_register_uri;
                }

                public String getApi_login_uri() {
                    return api_login_uri;
                }

                public void setApi_login_uri(String api_login_uri) {
                    this.api_login_uri = api_login_uri;
                }

                public String getActive() {
                    return active;
                }

                public void setActive(String active) {
                    this.active = active;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public static class InfoBean {
                    /**
                     * uid : 10
                     * taxi : 1
                     * taxiType : 2
                     * taxiAccount : 13521106013
                     * taxiTime : 1501747322
                     * taxiPass : 111111
                     */

                    private String uid;
                    private String taxi;
                    private String taxiType;
                    private String taxiAccount;
                    private String taxiTime;
                    private String taxiPass;

                    public String getUid() {
                        return uid;
                    }

                    public void setUid(String uid) {
                        this.uid = uid;
                    }

                    public String getTaxi() {
                        return taxi;
                    }

                    public void setTaxi(String taxi) {
                        this.taxi = taxi;
                    }

                    public String getTaxiType() {
                        return taxiType;
                    }

                    public void setTaxiType(String taxiType) {
                        this.taxiType = taxiType;
                    }

                    public String getTaxiAccount() {
                        return taxiAccount;
                    }

                    public void setTaxiAccount(String taxiAccount) {
                        this.taxiAccount = taxiAccount;
                    }

                    public String getTaxiTime() {
                        return taxiTime;
                    }

                    public void setTaxiTime(String taxiTime) {
                        this.taxiTime = taxiTime;
                    }

                    public String getTaxiPass() {
                        return taxiPass;
                    }

                    public void setTaxiPass(String taxiPass) {
                        this.taxiPass = taxiPass;
                    }
                }
            }
        }
    }
}
