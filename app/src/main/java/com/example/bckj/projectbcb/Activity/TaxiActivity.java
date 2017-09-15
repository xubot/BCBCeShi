package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiOneParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.QuXiaoDingDaiUtils;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class TaxiActivity extends BaseActivity {

    private ImageView taxiim;
    private ImageView taxicall;
    private TextView cancel;
    private String oid;
    private TextView taxi_name;
    private ImageView star_one;
    private ImageView star_twe;
    private ImageView star_three;
    private ImageView star_frou;
    private TextView bill;
    private ImageView star_five;
    private TextView taxinumer;
    private SharedUtils instance;

    //取消订单的  handler对象
    Handler getCancelOrderHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            //取出消息内容
            Object what = msg.obj;
            Log.d("zzz1", (String) what);
            if(what.equals("success")) {
                Toast.makeText(TaxiActivity.this, "你已成功取消行程", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_taxi);
        //得到存储信息的工具类
        instance = SharedUtils.getInstance();
        Intent intent = getIntent();
        oid = intent.getStringExtra("oid");
        Log.d("zzz", "从打车成功页面得到的oid:" + oid);

        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        //查看司机信息
        getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId",oid);

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
        //司机的信息的控件
        taxi_name = (TextView) findViewById(R.id.taxi_name);
        star_one = (ImageView) findViewById(R.id.star_one);
        star_twe = (ImageView) findViewById(R.id.star_twe);
        star_three = (ImageView) findViewById(R.id.star_three);
        star_frou = (ImageView) findViewById(R.id.star_frou);
        star_five = (ImageView) findViewById(R.id.star_five);
        bill = (TextView) findViewById(R.id.bill);
        taxinumer = (TextView) findViewById(R.id.taxinumer);
    }
    //控件的点击事件
    @Override
    public void cheked(){
        //调起聊天按钮监听
        taxiim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到手机司机did
                String driverDid = (String) instance.getData(TaxiActivity.this, "driverDid", "");
                Log.d("zzz", "TaxiActivity    得到的司机的Did：" + driverDid);
                //得到手机司机手机号
                String driverPhone = (String) instance.getData(TaxiActivity.this, "driverPhone", "");
                Log.d("zzz", "TaxiActivity   得到的司机的手机号：" + driverPhone);
                //得到手机司机名字
                String driverName = (String) instance.getData(TaxiActivity.this, "driverName", "");
                Log.d("zzz", "TaxiActivity   得到的司机的手机号：" + driverName);

                String driverOid = (String) instance.getData(TaxiActivity.this, "driverOid", "");
                Log.d("zzz", "TaxiActivity   得到的司机的oid：" + driverOid);

                String driverSkey = (String) instance.getData(TaxiActivity.this, "driverSkey", "");
                Log.d("zzz", "TaxiActivity   得到的司机的Skey：" + driverSkey);

                Intent intent = new Intent(TaxiActivity.this, TaxiImActivity.class);
                intent.putExtra("did",driverDid);
                intent.putExtra("driverPhone",driverPhone);
                intent.putExtra("driverName",driverName);
                intent.putExtra("driverOid",driverOid);
                intent.putExtra("driverSkey",driverSkey);
                //启动跳转
                startActivity(intent);
            }
        });
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到取消订单的工具类
                QuXiaoDingDaiUtils quXiaoDingDaiUtils = new QuXiaoDingDaiUtils();
                //得到当前时间，在当前时间的基础上加5分钟
                Date date = new Date();
                Date afterDate= new Date(date.getTime()+300000);
                SimpleDateFormat format = new SimpleDateFormat("hh : mm");
                String format1 = format.format(afterDate);
                //请求取消等待的方法
                getCancelOrderTaskIDRequest(quXiaoDingDaiUtils,oid,format1);

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
                backgroundThreadShortToast(TaxiActivity.this,"找不到网关地址,请重启设备");
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
                Toast.makeText(TaxiActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                            sleep(500);
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
                    //得到司机的数据
                    DingDaiLieBiaoBeanData dingDaiLieBiaoBeanData = gson.fromJson(returnJSONStr, DingDaiLieBiaoBeanData.class);
                    int status1 = dingDaiLieBiaoBeanData.getStatus();
                    String oid = dingDaiLieBiaoBeanData.getOid();

                    instance.saveData(TaxiActivity.this,"driverOid",oid);

                    if(status1==5){
                        Log.d("zzzstatus1", "TaxiActivity   得到订单的状态码："+status1);
                        //调起请求数据的方法
                        getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId", TaxiActivity.this.oid);
                    }else if(status1==4){
                        DingDaiLieBiaoBeanData.ImInfoBean imInfo = dingDaiLieBiaoBeanData.getImInfo();
                        String skey = imInfo.getSkey();
                        instance.saveData(TaxiActivity.this,"driverSkey",skey);
                        String uname = imInfo.getUname();
                        String uid = imInfo.getUid();
                        DingDaiLieBiaoBeanData.TaxiDriverBean taxiDriver = dingDaiLieBiaoBeanData.getTaxiDriver();
                        if(taxiDriver!=null){
                            final String card = taxiDriver.getCard();
                            final String name = taxiDriver.getName();
                            String phone = taxiDriver.getPhone();
                            final int level = taxiDriver.getLevel();
                            final int orderCount = taxiDriver.getOrderCount();
                            String did = taxiDriver.getDid();
                            Log.d("zzz", "TaxiActivity   得到的司机信息时"+card+"--"+uname+"=="+phone+"==="+level+"---"+orderCount+"--"+did);

                            //将后面需要的值存起来
                            instance.saveData(TaxiActivity.this,"driverPhone",phone);
                            instance.saveData(TaxiActivity.this,"driverDid",uid);
                            instance.saveData(TaxiActivity.this,"driverName",uname);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    taxi_name.setText(name);
                                    taxinumer.setText(card);
                                    bill.setText(orderCount+"单");
                                    if(level==1){
                                        star_one.setImageResource(R.mipmap.icon_08);
                                    }else if(level==2){
                                        star_twe.setImageResource(R.mipmap.icon_08);
                                        star_one.setImageResource(R.mipmap.icon_08);
                                    }else if(level==3){
                                        star_twe.setImageResource(R.mipmap.icon_08);
                                        star_one.setImageResource(R.mipmap.icon_08);
                                        star_three.setImageResource(R.mipmap.icon_08);
                                    }else if(level==4){
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
                            });
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
                backgroundThreadShortToast(TaxiActivity.this,"找不到网关地址,请重启设备");
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
                Toast.makeText(TaxiActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                    Message obtain = Message.obtain();
                    obtain.obj=returnJSONStr;
                    getCancelOrderHandler.sendMessage(obtain);
                }
            }
        });
    }

    //解决在子线程中吐司的方法
    public  void backgroundThreadShortToast(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
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
    @Override
    protected void load() {

    }
}
