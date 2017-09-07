package com.example.bckj.projectbcb.Utils.DiDiUtils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/4.
 */

public class PathUrl {
    public static String ip;

    public static void getIp(Context context){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        //得到转换后打的ip地址
        ip = intToIp(ipAddress);
        Log.d("zzz","得到ip地址是："+ip);
    }
    //将得到的int类型的IP转换为String类型
    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
    public String URLTASK="http://"+ip+":2805/tasks";
    public String URLDATA="http://"+ip+":2805/tasks/";
}
