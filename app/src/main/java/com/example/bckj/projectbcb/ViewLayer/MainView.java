package com.example.bckj.projectbcb.ViewLayer;

import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.LogBean;
import com.example.bckj.projectbcb.Bean.SensitizelistBean;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface MainView {
    void getDataName(DataNameBean dataNameBean);
    void log(LogBean logBean);
    void getsensitizelistBean(SensitizelistBean sensitizelistBean);
}
