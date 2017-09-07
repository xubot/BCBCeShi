package com.example.bckj.projectbcb.ViewLayer;


import com.example.bckj.projectbcb.Bean.ReActiveUserBean;
import com.example.bckj.projectbcb.Bean.SensitizeBean;
import com.example.bckj.projectbcb.Bean.StatusBean;

/**
 * Created by Administrator on 2017/8/3.
 */

public interface SensitizeView {
    //激活自身App
    void status(StatusBean statusBean);
    //重新激活
    void reActiveData(ReActiveUserBean reActiveUserBean);
    void getSensitize(SensitizeBean sensitizeBean);
}
