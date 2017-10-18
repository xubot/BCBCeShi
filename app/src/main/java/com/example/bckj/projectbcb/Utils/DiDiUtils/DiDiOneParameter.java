package com.example.bckj.projectbcb.Utils.DiDiUtils;

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

public class DiDiOneParameter {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url(PathUrl.URLTASK)
                .post(body)
                .build();
        Call call = client.newCall(request);
        return call;
    }

    public Call okUitls1(String taskId){
        Log.d("zzz", "请求下来的taskid:"+taskId);
        String path=PathUrl.URLDATA+taskId+"";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url(path)
                .build();
        Log.d("zzz","最终的url:"+path);
        Call call = client.newCall(request);
        return call;
    }
}
