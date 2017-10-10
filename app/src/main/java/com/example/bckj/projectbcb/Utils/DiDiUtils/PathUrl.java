package com.example.bckj.projectbcb.Utils.DiDiUtils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Administrator on 2017/9/4.
 */

public class PathUrl {
    private static String ip;

    public static void getIp(Context context){
        WifiManager wm = (WifiManager)context.getSystemService(WIFI_SERVICE);
        DhcpInfo di = wm.getDhcpInfo();
        long getewayIpL=di.gateway;
        StringBuffer sb=new StringBuffer();
        sb.append(String.valueOf((int)(getewayIpL&0xff)));
        sb.append('.');
        sb.append(String.valueOf((int)((getewayIpL>>8)&0xff)));
        sb.append('.');
        sb.append(String.valueOf((int)((getewayIpL>>16)&0xff)));
        sb.append('.');
        sb.append(String.valueOf((int)((getewayIpL>>24)&0xff)));
        //网关地址
        ip = sb.toString();
        Log.d("zzz", "得到的网关地址：" + ip);
    }
    String URLTASK="http://192.168.0.1:2805/tasks";
    String URLDATA="http://192.168.0.1:2805/tasks/";
}
