package com.example.bckj.projectbcb.Utils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Administrator on 2017/8/31.
 */

public class DecideWifi {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String ip;

    public Call okUitls(String methodName, String name, String phone){
        OkHttpClient client = new OkHttpClient();
        String json="{\n" +
                "  \"androidId\": \"d81e4d43835c5dbb\",\n" +
                "  \"pkgName\": \"com.sdu.didi.psnger\",\n" +
                "  \"versionName\": \"5.1.4\",\n" +
                "  \"methodName\": \""+methodName+"\",\n" +
                "  \"argsJSONStr\": \"{\\\""+name+"\\\":\\\""+phone+"\\\"}\"\n" +
                "}";
        Log.d("zzz", "请求返回的请求体："+json);
        if(ip.equals("192.168.0.1")){
            Log.d("zzz333", json);
            RequestBody body = RequestBody.create(JSON, json);
            Log.d("zzz22", body+"");
            Request request = new Request.Builder()
                    .addHeader("Accept","*/*")
                    .url("http://192.168.0.1:2805/tasks")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            return call;
        }else {
            return null;
        }
    }
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
}
