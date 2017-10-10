package com.example.bckj.projectbcb.Utils.update;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/9/29.
 */

public class Utils {
    public Call okUitls() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Accept","*/*")
                .url("http://118.190.91.24//sc/index.php?s=/home/version/index")
                .build();
        Call call = client.newCall(request);
        return call;
    }
}
