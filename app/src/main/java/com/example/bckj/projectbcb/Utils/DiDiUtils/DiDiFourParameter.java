package com.example.bckj.projectbcb.Utils.DiDiUtils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

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
    public Call okUitls(String startAddress,String endAddress,String slat,String slng,String elat,String elng){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        String json="{\n" +
                "  \"androidId\": \"d81e4d43835c5dbb\",\n" +
                "  \"pkgName\": \"com.sdu.didi.psnger\",\n" +
                "  \"versionName\": \"5.1.4\",\n" +
                "  \"methodName\": \"callTaxi\",\n" +
                "  \"argsJSONStr\": \"{\\\"uId1\\\":\\\"\\\",\\\"uId2\\\":\\\"\\\",\\\"displayName1\\\":\\\""+startAddress+"\\\",\\\"displayName2\\\":\\\""+endAddress+"\\\",\\\"lat1\\\":"+slat+",\\\"lat2\\\":"+elat+",\\\"lng1\\\":"+slng+",\\\"lng2\\\":"+elng+"}\"\n" +
                "}";
        Log.d("zzz333", json);
        RequestBody body = RequestBody.create(JSON, json);
        Log.d("zzz22", body+"");
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url(PathUrl.URLTASK)
                .post(body)
                .build();
        Call call = client.newCall(request);
        return call;
    }


    public Call okUitls1(String taskId){
        Log.d("zzz1", taskId);
        String path=PathUrl.URLDATA+taskId+"";
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
