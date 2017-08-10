package com.example.bckj.projectbcb.Activity;

import android.view.View;
import android.widget.ImageView;

import com.example.bckj.projectbcb.R;

public class TaxiActivity extends BaseActivity {

    private ImageView taxiim;

    @Override
    public void initView() {
        setContentView(R.layout.activity_taxi);
        setToolBar("等待应答",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
    }

    @Override
    protected void init() {
        //得到聊天的控件
        taxiim = (ImageView) findViewById(R.id.taxiim);
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
            }
        });
    }

    @Override
    protected void load() {

    }
}
