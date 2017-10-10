package com.example.bckj.projectbcb.Bean.DiDiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class GetPicCodeBeanData {


    public int errorCode;
    public String errorMessage;
    public ResultBean result;

    public class ResultBean{
        public List<Object> values;
    }
}
