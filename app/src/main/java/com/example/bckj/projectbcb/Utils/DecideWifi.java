package com.example.bckj.projectbcb.Utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/31.
 */

public class DecideWifi {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String ip;

    public Call okUitls(String methodName, String name, String phone){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        String json="{\n" +
                "  \"androidId\": \"d81e4d43835c5dbb\",\n" +
                "  \"pkgName\": \"com.sdu.didi.psnger\",\n" +
                "  \"versionName\": \"5.1.4\",\n" +
                "  \"methodName\": \""+methodName+"\",\n" +
                "  \"argsJSONStr\": \"{\\\""+name+"\\\":\\\""+phone+"\\\"}\"\n" +
                "}";
        Log.d("zzz", "请求返回的请求体："+json);

        RequestBody body = RequestBody.create(JSON, json);
        Log.d("zzz22", body+"");
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url("http://192.168.0.1:2805/tasks")
                .post(body)
                .build();
        Call call = client.newCall(request);
        return call;
    }
}
