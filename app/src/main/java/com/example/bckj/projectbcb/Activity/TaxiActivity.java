package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiTaskIdBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiZhuCeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DingDaiLieBiaoBeanData;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.DecideWifiAlertdialog;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiOneParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.QuXiaoDingDaiUtils;
import com.example.bckj.projectbcb.Utils.OnMultiClickListener;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class TaxiActivity extends BaseActivity {
    private SharedUtils instance = SharedUtils.getInstance();
    private Timer timer=new Timer();

    private int driverOrderCount,status1, driverLeve;
    private TextView cancel, taxi_name, bill, taxinumer;
    private String driverDid,driverPhone,driverName, driverOid, driverSkey,driverCard;
    private ImageView taxiim, taxicall, star_one, star_twe,star_three, star_frou,star_five;


    @Override
    public void initView() {
        setContentView(R.layout.activity_taxi);

        //司机的信息的控件
        taxi_name = (TextView) findViewById(R.id.taxi_name);
        star_one = (ImageView) findViewById(R.id.star_one);
        star_twe = (ImageView) findViewById(R.id.star_twe);
        star_three = (ImageView) findViewById(R.id.star_three);
        star_frou = (ImageView) findViewById(R.id.star_frou);
        star_five = (ImageView) findViewById(R.id.star_five);
        bill = (TextView) findViewById(R.id.bill);
        taxinumer = (TextView) findViewById(R.id.taxinumer);


        //得到手机司机did
        driverDid = (String) instance.getData(TaxiActivity.this, "driverDid", "");
        Log.d("zzz", "TaxiActivity    得到的司机的Did：" + driverDid);
        //得到手机司机手机号
        driverPhone = (String) instance.getData(TaxiActivity.this, "driverPhone", "");
        Log.d("zzz", "TaxiActivity   得到的司机的手机号：" + driverPhone);
        //得到手机司机名字
        driverName = (String) instance.getData(TaxiActivity.this, "driverName", "");
        Log.d("zzz", "TaxiActivity   得到的司机的手机号：" + driverName);
        //得到司机的oid
        driverOid = (String) instance.getData(TaxiActivity.this, "driveroid", "");
        Log.d("zzz", "TaxiActivity   得到的司机的oid：" + driverOid);
        //得到司机的Skey
        driverSkey = (String) instance.getData(TaxiActivity.this, "driverSkey", "");
        Log.d("zzz", "TaxiActivity   得到的司机的Skey：" + driverSkey);
        //得到司机的车牌
        driverCard = (String) instance.getData(TaxiActivity.this, "driverCard", "");
        Log.d("zzz", "TaxiActivity   得到的司机的driverCard：" + driverCard);
        //得到司机的评分
        driverLeve = (int) instance.getData(TaxiActivity.this, "driverLevel", 0);
        Log.d("zzz", "TaxiActivity   得到的司机的driverLevel：" + this.driverLeve);
        //得到司机的接单数
        driverOrderCount = (int) instance.getData(TaxiActivity.this, "driverOrderCount", 0);
        Log.d("zzz", "TaxiActivity   得到的司机的driverOrderCount：" + driverOrderCount);
        status1 = (int) instance.getData(TaxiActivity.this, "status1",0);
        Log.d("zzz", "TaxiActivity   得到的司机的status1：" + status1);

        if(status1==4){
            //给控件赋值
            taxi_name.setText(driverName);
            taxinumer.setText(driverCard);
            bill.setText(driverOrderCount+"单");
            if(this.driverLeve ==1){
                star_one.setImageResource(R.mipmap.icon_08);
            }else if(this.driverLeve ==2){
                star_twe.setImageResource(R.mipmap.icon_08);
                star_one.setImageResource(R.mipmap.icon_08);
            }else if(this.driverLeve ==3){
                star_twe.setImageResource(R.mipmap.icon_08);
                star_one.setImageResource(R.mipmap.icon_08);
                star_three.setImageResource(R.mipmap.icon_08);
            }else if(this.driverLeve ==4){
                star_twe.setImageResource(R.mipmap.icon_08);
                star_one.setImageResource(R.mipmap.icon_08);
                star_three.setImageResource(R.mipmap.icon_08);
                star_frou.setImageResource(R.mipmap.icon_08);
            }else {
                star_twe.setImageResource(R.mipmap.icon_08);
                star_one.setImageResource(R.mipmap.icon_08);
                star_three.setImageResource(R.mipmap.icon_08);
                star_frou.setImageResource(R.mipmap.icon_08);
                star_five.setImageResource(R.mipmap.icon_08);
            }
        }
        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        //查看司机信息
        getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId",driverOid);

        setToolBar("等待应答",R.mipmap.back_02,R.color.one);
    }

    @Override
    protected void init() {
        //打电话控件
        taxicall = (ImageView) findViewById(R.id.taxicall);
        //取消控件
        cancel = (TextView) findViewById(R.id.cancel);
    }
    //控件的点击事件
    @Override
    public void cheked(){
        //调起打电话的控件监听
        taxicall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到手机司机手机号
                String driverPhone = (String) instance.getData(TaxiActivity.this, "driverPhone", "");
                Log.d("zzz", "TaxiActivity   得到的司机的手机号：" + driverPhone);
                Intent tn = new Intent("android.intent.action.CALL", Uri.parse("tel:"+driverPhone));
                startActivity(tn);
            }
        });
        //取消订单监听
        cancel.setOnClickListener(new OnMultiClickListener(){
            @Override
            public void onMultiClick(View v) {
                //得到取消订单的工具类
                QuXiaoDingDaiUtils quXiaoDingDaiUtils = new QuXiaoDingDaiUtils();
                //得到当前时间，在当前时间的基础上加5分钟
                Date date = new Date();
                Date afterDate= new Date(date.getTime()+540000);
                SimpleDateFormat format = new SimpleDateFormat("HH : mm");
                String format1 = format.format(afterDate);
                Log.d("zzz", "得到当前的24进制时间：" + format1);
                //请求取消等待的方法
                getCancelOrderTaskIDRequest(quXiaoDingDaiUtils,driverOid,format1);
            }
        });

        /*//取消订单监听
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到取消订单的工具类
                QuXiaoDingDaiUtils quXiaoDingDaiUtils = new QuXiaoDingDaiUtils();
                //得到当前时间，在当前时间的基础上加5分钟
                Date date = new Date();
                Date afterDate= new Date(date.getTime()+540000);
                SimpleDateFormat format = new SimpleDateFormat("HH : mm");
                String format1 = format.format(afterDate);
                Log.d("zzz", "得到当前的24进制时间：" + format1);
                //请求取消等待的方法
                getCancelOrderTaskIDRequest(quXiaoDingDaiUtils,driverOid,format1);
            }
        });*/
    }

    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //调起得到订单详情 滴滴的TaskId的请求方法
    private void getTaxiOrderTaskIDRequest(final DiDiOneParameter diDiOneParameter, String modle, String parameter, String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(TaxiActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                getTaxiOrderDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起订单详情 滴滴的数据请求的方法
    private void getTaxiOrderDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "TaxiActivity  请求过程中错误的信息：--" + e.toString());
                threadToast(TaxiActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","TaxiActivity   得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","TaxiActivity   得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaxiActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        getTaxiOrderDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "TaxiActivity   当前的状态值：" + status);
                        return;
                    }

                    Log.d("zzz", "TaxiActivity   当前的状态值：" + status);
                    Log.d("zzz", "TaxiActivity   终于到了done:" + returnJSONStr);

                    if(returnJSONStr.equals("Listener")){
                        //调起请求数据的方法
                        getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId", driverOid);
                        threadToast(TaxiActivity.this,"异常");
                    }else {
                        //得到司机的数据
                        DingDaiLieBiaoBeanData dingDaiLieBiaoBeanData = gson.fromJson(returnJSONStr, DingDaiLieBiaoBeanData.class);
                        int status1 = dingDaiLieBiaoBeanData.getStatus();
                        if(status1==2){
                            timer.cancel();
                            threadToast(TaxiActivity.this,"订单已关闭");
                            finish();
                        }else if(status1==3){
                            timer.cancel();
                            threadToast(TaxiActivity.this,"行程已完成");
                            finish();
                        }else if(status1==4){
                            //threadToast(TaxiActivity.this,"4:"+status1);
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //得到一个参数的对象
                                    DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                                    //查看司机信息
                                    getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId",driverOid);
                                }
                            },5000);
                        }else {
                            threadToast(TaxiActivity.this,"正在行程中1:"+status1);
                            //timer.cancel();
                            /*timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //得到一个参数的对象
                                    DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                                    //查看司机信息
                                    getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId",driverOid);
                                }
                            },5000);*/
                        }
                    }
                }
            }
        });
    }


    //调起取消订单 滴滴的TaskId的请求方法
    private void getCancelOrderTaskIDRequest(final QuXiaoDingDaiUtils quXiaoDingDaiUtils,String oid,String time) {
        Call oneCall = quXiaoDingDaiUtils.okUitls(oid,time);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(TaxiActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                getCancelOrderDataRequest(quXiaoDingDaiUtils,taskId);
            }
        });
    }
    //调起取消订单 滴滴的数据请求的方法
    private void getCancelOrderDataRequest(final QuXiaoDingDaiUtils quXiaoDingDaiUtils, final String taskId) {
        Call tweCall = quXiaoDingDaiUtils.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(TaxiActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaxiActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
                            //得到取消订单的工具类
                            QuXiaoDingDaiUtils quXiaoDingDaiUtils = new QuXiaoDingDaiUtils();
                            //得到当前时间，在当前时间的基础上加5分钟
                            Date date = new Date();
                            Date afterDate= new Date(date.getTime()+540000);
                            SimpleDateFormat format = new SimpleDateFormat("HH : mm");
                            String format1 = format.format(afterDate);
                            Log.d("zzz", "得到当前的24进制时间：" + format1);
                            //请求取消等待的方法
                            getCancelOrderTaskIDRequest(quXiaoDingDaiUtils,driverOid,format1);
                        }
                    });
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        getCancelOrderDataRequest(quXiaoDingDaiUtils,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    if(returnJSONStr.equals("success")){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(TaxiActivity.this, "你已成功取消行程", Toast.LENGTH_SHORT).show();
                                timer.cancel();
                                finish();
                            }
                        });
                    }else {
                        //得到取消订单的工具类
                        QuXiaoDingDaiUtils quXiaoDingDaiUtils = new QuXiaoDingDaiUtils();
                        //得到当前时间，在当前时间的基础上加5分钟
                        Date date = new Date();
                        Date afterDate= new Date(date.getTime()+540000);
                        SimpleDateFormat format = new SimpleDateFormat("HH : mm");
                        String format1 = format.format(afterDate);
                        Log.d("zzz", "得到当前的24进制时间：" + format1);
                        //请求取消等待的方法
                        getCancelOrderTaskIDRequest(quXiaoDingDaiUtils,driverOid,format1);
                        //threadToast(TaxiActivity.this,"请重新点击取消");
                    }
                }
            }
        });
    }

    //解决在子线程中吐司的方法
    public  void threadToast(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void load() {}
}
