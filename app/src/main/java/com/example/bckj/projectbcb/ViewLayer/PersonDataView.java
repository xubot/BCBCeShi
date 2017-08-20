package com.example.bckj.projectbcb.ViewLayer;

import com.example.bckj.projectbcb.Bean.LogoutBean;
import com.example.bckj.projectbcb.Bean.PersonDataBean;
import com.example.bckj.projectbcb.Bean.ReActiveUserBean;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface PersonDataView {
    void personData(PersonDataBean personDataBean);
    void reActiveData(ReActiveUserBean reActiveUserBean);
    void getLogout(LogoutBean logoutBean);
}
