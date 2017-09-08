package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Adapter.Mylv1Adapter;
import com.example.bckj.projectbcb.Bean.ListViewBean_1;
import com.example.bckj.projectbcb.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaxiImActivity extends BaseActivity {
    private List<ListViewBean_1> lvb_1=new ArrayList<>();
    private ImageView taxi_view_call;
    private ListView taxi_lv;
    private EditText messge;
    private ImageView msg;

    @Override
    public void initView() {
        setContentView(R.layout.activity_taxi_im);
        setToolBar("等待应答",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
    }

    @Override
    protected void init() {
        //调起打电话的控件
        taxi_view_call = (ImageView) findViewById(R.id.taxi_view_call);
        //得到listView控件
        taxi_lv = (ListView) findViewById(R.id.taxi_lv);
        //得到发送消息的控件
        messge = (EditText) findViewById(R.id.taxi_view_ll_messge);
        //得到发送的控件
        msg = (ImageView) findViewById(R.id.taxi_view_ll_msg);
    }

    @Override
    public void cheked() {
        //打电话的监听方法
        taxi_view_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tn = new Intent("android.intent.action.CALL", Uri.parse("tel:"+"18513433864"));
                startActivity(tn);
            }
        });
        //发送消息的监听
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到要发送的信息
                String messgee = messge.getText().toString();
                Log.d("messgee", "taxi  输入的信息："+messgee.length());
                if(messgee.length()==0){
                    Toast.makeText(TaxiImActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("hh : mm");
                    String format1 = format.format(date);
                    ListViewBean_1 listViewBean_1 = new ListViewBean_1(messgee,format1,R.mipmap.img_01);
                    lvb_1.add(listViewBean_1);
                    //得到listview的适配器
                    Mylv1Adapter mylv1Adapter = new Mylv1Adapter(TaxiImActivity.this, lvb_1);
                    taxi_lv.setAdapter(mylv1Adapter);
                    //listview实时刷新
                    mylv1Adapter.notifyDataSetChanged();
                    messge.setText("");
                }
            }
        });
    }

    @Override
    protected void load() {

    }
    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxiImActivity.this,TaxiActivity.class));
                finish();
            }
        });
    }
}
