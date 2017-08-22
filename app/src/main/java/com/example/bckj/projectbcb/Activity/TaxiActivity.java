package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.R;

public class TaxiActivity extends BaseActivity {

    private ImageView taxiim;
    private ImageView taxicall;
    private TextView cancel;

    @Override
    public void initView() {
        setContentView(R.layout.activity_taxi);
        setToolBar("等待应答",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
    }

    @Override
    protected void init() {
        //得到聊天的控件
        taxiim = (ImageView) findViewById(R.id.taxiim);
        //打电话控件
        taxicall = (ImageView) findViewById(R.id.taxicall);
        //取消控件
        cancel = (TextView) findViewById(R.id.cancel);
    }
    //控件的点击事件
    @Override
    public void cheked(){
        //调起聊天按钮监听
        taxiim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_taxi_view);
                setToolBar("等待应答",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
                //调起打电话的控件监听
                ImageView taxi_view_call= (ImageView) findViewById(R.id.taxi_view_call);
                taxi_view_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent tn = new Intent("android.intent.action.CALL", Uri.parse("tel:"+"18513433864"));
                        startActivity(tn);
                    }
                });
            }
        });
        //调起打电话的控件监听
        taxicall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tn = new Intent("android.intent.action.CALL", Uri.parse("tel:"+"18513433864"));
                startActivity(tn);
            }
        });
        //取消订单监听
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaxiActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxiActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void load() {

    }
}
