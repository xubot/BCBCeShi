package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Adapter.Mylv1Adapter;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiTaskIdBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiZhuCeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.FaXiaoXiBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.FaXiaoXiBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.ShouXiaoXiBeanData;
import com.example.bckj.projectbcb.Bean.ListViewBean_1;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiOneParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.LiaoTianUtils;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class TaxiImActivity extends BaseActivity {
    private SharedUtils instance = SharedUtils.getInstance();
    private Timer timer = new Timer();
    private List<ListViewBean_1> lvb_1=new ArrayList<>();
    private int i=0;
    private TextView taxi_im_name;
    private ListView taxi_lv;
    private EditText messge;
    private ImageView msg, taxi_view_call;
    private String driverPhone, did, driverSkey,driverOid,dName;



    @Override
    public void initView() {
        setContentView(R.layout.activity_taxi_im);

        //得到司机师傅名字的控件
        taxi_im_name = (TextView) findViewById(R.id.taxi_im_name);
        setToolBar("等待应答",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
        //得到意图
        Intent intent = getIntent();
        //得到意图里面的值
        did = intent.getStringExtra("did");
        driverPhone = intent.getStringExtra("driverPhone");
        String driverName = intent.getStringExtra("driverName");
        dName = stringToUnicode(driverName);
        driverSkey = intent.getStringExtra("driverSkey");
        driverOid = intent.getStringExtra("driverOid");
        Log.d("zzz", "TaxiImActivity   得到的跳过来司机的Did：" + did +"===="+ driverPhone+"----"+driverName +"=="+driverSkey+"--"+driverOid);
        //给司机名字控件赋值
        taxi_im_name.setText(driverName);

        /*//调起得到司机回复的信息的接口
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        getReceiveMessageTaskIDRequest(diDiOneParameter,"receiveMessage","dId",did);
        Log.d("zzzTaxi", "现在已经调起了接收司机回复的消息接口");*/
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
                Intent tn = new Intent("android.intent.action.CALL", Uri.parse("tel:"+driverPhone));
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
                    SimpleDateFormat format = new SimpleDateFormat("HH : mm");
                    String format1 = format.format(date);
                    ListViewBean_1 listViewBean_1 = new ListViewBean_1();
                    listViewBean_1.setImg(R.mipmap.img_01);
                    listViewBean_1.setTitle(messgee);
                    listViewBean_1.setTime(format1);
                    lvb_1.add(listViewBean_1);
                    //得到listview的适配器
                    Mylv1Adapter mylv1Adapter = new Mylv1Adapter(TaxiImActivity.this, lvb_1);
                    //listview实时刷新
                    mylv1Adapter.notifyDataSetChanged();
                    taxi_lv.setAdapter(mylv1Adapter);

                    messge.setText("");
                    String vaule = stringToUnicode(messgee);
                    
                    //得到一个参数的对象
                    LiaoTianUtils liaoTianUtils = new LiaoTianUtils();
                    String taxiaccount = (String) instance.getData(TaxiImActivity.this, "taxiaccount", "");
                    Log.d("zzz", "得到自己的手机号：" + taxiaccount);
                    if(did.isEmpty()&&driverOid.isEmpty()&&driverSkey.isEmpty()&&dName.isEmpty()&&taxiaccount.isEmpty()){
                        return;
                    }else {
                        //发送消息
                        sendMessageTaskIDRequest(liaoTianUtils,"sendMessage",vaule,did,driverOid,driverSkey,dName,taxiaccount);
                    }
                }
            }
        });
    }

    //将字符串转成uniCode
    public String stringToUnicode(String vaule) {
        String str = "";
        for (int i = 0; i < vaule.length(); i++) {
            int ch = (int) vaule.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += "\\" + Integer.toHexString(ch);
        }
        return str;
    }

    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });
    }


    //调起得到与司机聊天   滴滴的TaskId的请求方法
    private void sendMessageTaskIDRequest(final LiaoTianUtils liaoTianUtils, String modle, String parameter, String vaule,String oid,String skey,String name,String phone) {
        Call oneCall = liaoTianUtils.okUitls(modle,parameter,vaule,oid,skey,name,phone);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                threadToast(TaxiImActivity.this,"找不到网关地址,请重启设备");
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
                sendMessageDataRequest(liaoTianUtils,taskId);
            }
        });
    }
    //调起与司机聊天 滴滴的数据请求的方法
    private void sendMessageDataRequest(final LiaoTianUtils liaoTianUtils, final String taskId) {
        Call tweCall = liaoTianUtils.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzzTaxi", "请求过程中错误的信息：--" + e.toString());
                threadToast(TaxiImActivity.this,"连接超时，请重试");
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzzTaxi","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzzTaxi","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaxiImActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        sendMessageDataRequest(liaoTianUtils,taskId);
                        Log.d("zzzTaxi", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzzTaxi", "聊天的状态当前的状态值：" + status);
                    Log.d("zzzTaxi", "聊天得到终于到了done:" + returnJSONStr);
                    FaXiaoXiBeanData faXiaoXiBeanData = gson.fromJson(returnJSONStr, FaXiaoXiBeanData.class);
                    String errorCode = faXiaoXiBeanData.getErrorCode();
                    String errorMessage = faXiaoXiBeanData.getErrorMessage();
                    if(errorCode.equals("0")){
                        FaXiaoXiBeanData.Result result = faXiaoXiBeanData.getResult();
                        String response1 = result.getResponse();
                        FaXiaoXiBean faXiaoXiBean = gson.fromJson(response1, FaXiaoXiBean.class);
                        int errno = faXiaoXiBean.getErrno();
                        if(errno==0){
                            DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                            getDBChatHistoryTaskIDRequest(diDiOneParameter,"receiveMessage","dId",did);
                            Log.d("zzzTaxi", "现在已经调起了接收司机回复的消息接口");
                        }
                    }else {
                        threadToast(TaxiImActivity.this,errorMessage);
                    }
                }
            }
        });
    }


    //调起得到司机回复的信息   滴滴的TaskId的请求方法
    private void getDBChatHistoryTaskIDRequest(final DiDiOneParameter diDiOneParameter, String modle, String parameter, String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                threadToast(TaxiImActivity.this,"找不到网关地址,请重启设备");
                Log.d("zzzTaxi", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzzTaxi","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                getDBChatHistoryDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起得到司机回复的信息 滴滴的数据请求的方法
    private void getDBChatHistoryDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzzTaxi", "请求过程中错误的信息：--" + e.toString());
                threadToast(TaxiImActivity.this,"连接超时，请重试");
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzzTaxi","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzzTaxi","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaxiImActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        getDBChatHistoryDataRequest(diDiOneParameter,taskId);
                        Log.d("zzzTaxi", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzzTaxi", "聊天的状态当前的状态值：" + status);
                    Log.d("zzzTaxi", "聊天得到终于到了done:" + returnJSONStr);
                    ShouXiaoXiBeanData shouXiaoXiBeanData = gson.fromJson(returnJSONStr, ShouXiaoXiBeanData.class);
                    String errorCode = shouXiaoXiBeanData.getErrorCode();
                    String errorMessage = shouXiaoXiBeanData.getErrorMessage();
                    String result = shouXiaoXiBeanData.getResult();
                    if(result.equals("No Data")){
                        Log.d("zzzTaxi","现在会返回的集合还是空啊，太让人郁闷了----------------");
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        getDBChatHistoryTaskIDRequest(diDiOneParameter,"receiveMessage","dId",did);
                    }else {
                        Log.d("zzzTaxi", "司机信息的集合终于不是空了:"+result);
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
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //得到司机信息赋值的方法
    public  void backgroundThreadShort() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d("zzzTaxi", "开始给司机赋值...." );
                for(ListViewBean_1 lv1:lvb_1){
                    int img = lv1.getImg();
                    String time = lv1.getTime();
                    String title = lv1.getTitle();
                    Log.d("zzzTaxi", "得到司机返回消息的集合里面的信息："+img + "--" + time + "===" + title);
                }
                //得到listview的适配器
                Mylv1Adapter mylv1Adapter = new Mylv1Adapter(TaxiImActivity.this, lvb_1);
                //listview实时刷新
                mylv1Adapter.notifyDataSetChanged();
                taxi_lv.setAdapter(mylv1Adapter);
            }
        });
    }

    @Override
    protected void load() {}
}
