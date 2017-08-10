package com.example.bckj.projectbcb.Moude;

import android.util.Log;

import com.example.bckj.projectbcb.Utils.net.SetCreate;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MoudelLayer {
    public Retrofit getData() {
        Log.d("zzz", "getData");
        return SetCreate.setCreate();
    }
}
