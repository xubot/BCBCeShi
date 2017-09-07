package com.example.bckj.projectbcb.Utils.DiDiUtils;

import android.util.Log;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/30.
 */

public class DiDiFourParameter {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public Call okUitls(String methodName, String startAddress,String endAddress,String sAddress,String eAddress){
        OkHttpClient client = new OkHttpClient();
        String json="{\n" +
                "  \"androidId\": \"d81e4d43835c5dbb\",\n" +
                "  \"pkgName\": \"com.sdu.didi.psnger\",\n" +
                "  \"versionName\": \"5.1.4\",\n" +
                "  \"methodName\": \""+methodName+"\",\n" +
                "  \"argsJSONStr\": \"{\\\""+startAddress+"\\\":\\\""+sAddress+"\\\",\\\""+endAddress+"\\\":\\\""+eAddress+"\\\",\\\"index1\\\":1,\\\"index2\\\":2}\"\n" +
                "}";
        Log.d("zzz333", json);
        RequestBody body = RequestBody.create(JSON, json);
        Log.d("zzz22", body+"");
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url(new PathUrl().URLTASK)
                .post(body)
                .build();
        Call call = client.newCall(request);
        return call;
    }


    public Call okUitls1(String taskId){
        Log.d("zzz1", taskId);
        String path=new PathUrl().URLDATA+taskId+"";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url(path)
                .build();
        Log.d("zzz2",path);
        Call call = client.newCall(request);
        return call;
    }
}
