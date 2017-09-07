package com.example.bckj.projectbcb.Utils.DiDiUtils;

import android.util.Log;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/31.
 */

public class DiDiTweParameter {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public Call okUitls(String methodName, String phone,String pwd){
        OkHttpClient client = new OkHttpClient();

        String json="{\n" +
                "  \"androidId\": \"d81e4d43835c5dbb\",\n" +
                "  \"pkgName\": \"com.sdu.didi.psnger\",\n" +
                "  \"versionName\": \"5.1.4\",\n" +
                "  \"methodName\": \""+methodName+"\",\n" +
                "  \"argsJSONStr\": \"{\\\"phoneNum\\\":\\\""+phone+"\\\",\\\"password\\\":\\\""+pwd+"\\\"}\"\n" +
                "}";

        Log.d("zzz", "请求返回的请求体："+json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url(new PathUrl().URLTASK)
                .post(body)
                .build();
        Call call = client.newCall(request);
        return call;
    }


    public Call okUitls1(String taskId){
        Log.d("zzz", "请求下来的taskid:"+taskId);
        String path=new PathUrl().URLDATA+taskId+"";
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
