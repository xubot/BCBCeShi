package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.NetJudge;

public class NetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        final ImageView img= (ImageView) findViewById(R.id.imageView);
        final boolean wife = NetJudge.isWife(NetActivity.this);
        Log.d("zzz", "得到当前的网络状态-：" + wife);
        if(wife){
            startActivity(new Intent(NetActivity.this,MainActivity.class));
            finish();
        }else {
            boolean net = NetJudge.isNet(NetActivity.this);
            Log.d("zzz", "得到当前的网络状态=：" + net);
            if(net){
                startActivity(new Intent(NetActivity.this,MainActivity.class));
                finish();
            }else {
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean net = NetJudge.isNet(NetActivity.this);
                        final boolean wife = NetJudge.isWife(NetActivity.this);
                        Log.d("zzz", "得到当前的网络状态+：" + wife+"=="+net);
                        netList(wife,net);
                    }
                });
            }
        }
    }

    private void netList(final boolean wife,final boolean net) {
        Log.d("zzz", "得到当前的网络状态xxx：" + wife+"--"+net);
        if(wife||net){
            Toast.makeText(this, "网络正常，正在为你进入主页", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NetActivity.this,MainActivity.class));
            finish();
        }else {
            Toast.makeText(this, "当前网络不可用，请检查网络是否正常", Toast.LENGTH_SHORT).show();
        }
    }

}
