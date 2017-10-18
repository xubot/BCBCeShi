package com.example.bckj.projectbcb.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.DiDiBean.DengLuBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiCodeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiTaskIdBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiZhuCeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.GetPicCodeBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.HuoQuYanZhengMaBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.SheZhiMiMaBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.SheZhiMiMaSuccessBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.ZhuCeBeanData;
import com.example.bckj.projectbcb.Bean.ReActiveUserBean;
import com.example.bckj.projectbcb.Bean.SensitizeBean;
import com.example.bckj.projectbcb.Bean.StatusBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.CountDownTimerUtils;
import com.example.bckj.projectbcb.Utils.DecideWifiAlertdialog;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiOneParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiTweParameter;
import com.example.bckj.projectbcb.Utils.OnMultiClickListener;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.SensitizeView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class SensitizeActivity extends AppCompatActivity implements SensitizeView{
    private PresenterLayer presenterLayer = new PresenterLayer();
    private SharedUtils instance = SharedUtils.getInstance();
    private Context context=SensitizeActivity.this;
    private AlertDialog alertDialog;
    private ImageView pic_code;
    private Button sensitize_s;
    private Button sens_log;
    private TextView resend,sensitize_pwd,chongxin,codephone, sensitize_car,sens_forget;
    private EditText et_one, et_two, et_three,et_four;
    private EditText pice_one,pice_twe,pice_three,pice_frou;
    private EditText sens_edit_phone,sens_log_edit_phone,sens_log_edit_pwd,sens_edit_pwd;
    private String token,sens_phone,log_edit_phone,pwd, log_edit_pwd;
    private EditText[] mArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitize);
        //得到注册的token值
        int code = (int) instance.getData(this, "mainLogCode", 0);
        Log.d("zzz", "sensitize  得到本地的登录状态码：" + code);
        //得到token值
        token = (String) instance.getData(this, "token", "");
        Log.d("zzz", "sensitize  得到本地的登录token值" + token);
        if(code==1){
            //得到返回控件
            LinearLayout back= (LinearLayout) findViewById(R.id.sensitizze_back);
            //得到关闭控件
            ImageView close= (ImageView) findViewById(R.id.sensitize_close);
            //得到下一步的按钮控件
            sensitize_s = (Button) findViewById(R.id.sensitize_s);
            //返回控件的点击监听
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
                    finish();
                }
            });
            //关闭控件的点击监听
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
                    finish();
                }
            });
            //开启请求
            load();
        }else {
            startActivity(new Intent(context,LogActivity.class));
            this.finish();
        }
    }

    //得到P层的对象
    public void load(){
        Log.d("zzz", "sensitize 得到token" + token);
        presenterLayer.SensitizeView(this);
        presenterLayer.setStatus(token);
    }

    //点击下一步的监听方法
    private void nextOnclivk() {
        sensitize_s.setOnClickListener(new OnMultiClickListener(){
            @Override
            public void onMultiClick(View v) {
                //得到一个参数的对象
                DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                //得到手机号码的输入框
                sens_edit_phone = (EditText)findViewById(R.id.sens_edit_phone);
                //得到手机号的值
                sens_phone = sens_edit_phone.getText().toString();
                boolean mobile = isMobile(sens_phone);
                Log.d("zzz",mobile+"");
                if(mobile&&!sens_phone.isEmpty()&&sens_phone.length()>0){
                    //调用滴滴的接口方法
                    registerTaskIDRequest(diDiOneParameter,"getRegisterStatus","phoneNum", sens_phone);
                }else {
                    Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //登录的对话框
    private void Pop_Log() {
        //打开登录的对话框
        alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_log);
        //得到输入手机号的控件
        sens_edit_phone = (EditText)findViewById(R.id.sens_edit_phone);
        //得到手机号的值
        String sens_phone = sens_edit_phone.getText().toString();
        //给登录的对话框电话处赋值
        sens_log_edit_phone.setText(sens_phone);
        //对话框里登录的监听
        sens_log.setOnClickListener(new OnMultiClickListener(){
            @Override
            public void onMultiClick(View v) {
                //得到两个参数的对象
                DiDiTweParameter diDiTweParameter = new DiDiTweParameter();
                //得到输入控上的用户名
                log_edit_phone = sens_log_edit_phone.getText().toString();
                //得到输入框上的密码
                log_edit_pwd = sens_log_edit_pwd.getText().toString();
                Log.d("zzz", "sensitize  登录的手机号和密码："+log_edit_phone +"==="+ log_edit_pwd);
                if(log_edit_phone.isEmpty()&&log_edit_phone.length()==0&&log_edit_pwd.isEmpty()&&log_edit_pwd.length()==0){
                    Toast.makeText(context, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                }else {
                    //调用登录滴滴的方法
                    logInTaskIDRequest(diDiTweParameter,"login",log_edit_phone,log_edit_pwd);
                }
            }
        });
        /*//对话框里忘记密码吗的监听
        sens_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopForgetOnClick();
                alertDialog.cancel();
            }
        });*/
    }


    //图形验证码的对话框
    private void setPhotoCodeOnClick(Object pic) {
        Log.d("zzz", "从子线程中得到的图形码：" + pic);

        byte[] bytes = hexStrToByteArray(String.valueOf(pic));
        //打开获得图形验证码的对话框
        alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_pic_code);
        //将byte转成bitmap对象
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.d("zzz", bitmap+"");
        //给图片验证码处赋值
        pic_code.setImageBitmap(bitmap);

        //滴滴输入验证码的方法
        PhotoCodeinitListener();
        /**点击图片换一张**/
        pic_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了重新换一张图片", Toast.LENGTH_SHORT).show();
                //得到一个参数的对象
                DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                //得到弹出框手机号的值
                String sens_phone1 = sens_edit_phone.getText().toString();
                //调起滴滴获得验证码的方法
                verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完图形验证码自动跳转）
    private void PhotoCodeinitListener() {
        mArray = new EditText[]{pice_one, pice_twe, pice_three,pice_frou};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (temp.length() == 1 && j >= 0 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }

                    if (temp.length() == 0) {
                        if (j >= 1) {
                            mArray[j - 1].setFocusable(true);
                            mArray[j - 1].setFocusableInTouchMode(true);
                            mArray[j - 1].requestFocus();
                        }
                    }
                    if (!TextUtils.isEmpty(pice_one.getText().toString().trim()) && !TextUtils.isEmpty(pice_twe.getText().toString().trim()) && !TextUtils.isEmpty(pice_three.getText().toString().trim()) && !TextUtils.isEmpty(pice_frou.getText().toString().trim())) {
                        String code = pice_one.getText().toString().trim() + pice_twe.getText().toString().trim() + pice_three.getText().toString().trim() + pice_frou.getText().toString().trim();
                        Toast.makeText(context,code, Toast.LENGTH_SHORT).show();
                        //得到一个参数的对象
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        //调起发送验证码的方法
                        setPhotoCodeTaskIDRequest(diDiOneParameter,"setPhotoCode","photoCode",code);
                    }
                }
            });
        }
    }

    //短信验证码对话框（没有注册过的手机号）
    private void setCodeOnClick() {
        Log.d("zzzz15", "sensitizexxxxxx");
        //得到一个参数的对象
        final DiDiOneParameter diDiOneParameter = new DiDiOneParameter();

        //打开获得短信验证码的对话框
        alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_code);
        //得到弹出框手机号的值
        final String sens_phone1 = sens_edit_phone.getText().toString();
        //将手机号赋给验证码处
        codephone.setText("验证码发送至   "+sens_phone1);

        //滴滴输入验证码的方法
        codeInitListener();

        //开启倒计时
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 70000, 1000);
        mCountDownTimerUtils.start();

        /**点击重新发送验证码**/
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了重新发送", Toast.LENGTH_SHORT).show();
                //开启倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 70000, 1000);
                mCountDownTimerUtils.start();
                //调起滴滴获得验证码的方法
                verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完验证码自动跳转）
    private void codeInitListener() {
        mArray = new EditText[]{et_one, et_two, et_three,et_four};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (temp.length() == 1 && j >= 0 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }

                    if (temp.length() == 0) {
                        if (j >= 1) {
                            mArray[j - 1].setFocusable(true);
                            mArray[j - 1].setFocusableInTouchMode(true);
                            mArray[j - 1].requestFocus();
                        }
                    }
                    if (!TextUtils.isEmpty(et_one.getText().toString().trim()) && !TextUtils.isEmpty(et_two.getText().toString().trim()) && !TextUtils.isEmpty(et_three.getText().toString().trim()) && !TextUtils.isEmpty(et_four.getText().toString().trim())) {
                        String code = et_one.getText().toString().trim() + et_two.getText().toString().trim() + et_three.getText().toString().trim() + et_four.getText().toString().trim();
                        Toast.makeText(context,code, Toast.LENGTH_SHORT).show();
                        //得到一个参数的对象
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        //调起发送验证码的方法
                        submiteCodeTaskIDRequest(diDiOneParameter,"submitEcode","smsCode",code);
                        Log.d("zzzz16", "sensitizexxxxxx");
                    }
                }
            });
        }
    }



    //图形验证码的对话框
    private void setPicCodeOnClick(Object pic) {
        Log.d("zzz", "从子线程中得到的图形码：" + pic);

        byte[] bytes = hexStrToByteArray(String.valueOf(pic));
        //打开获得图形验证码的对话框
        alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_pic_code);
        //将byte转成bitmap对象
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.d("zzz", bitmap+"");
        //给图片验证码处赋值
        pic_code.setImageBitmap(bitmap);

        //滴滴输入验证码的方法
        picCodeinitListener();
        /**点击图片换一张**/
        pic_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了重新换一张图片", Toast.LENGTH_SHORT).show();
                //得到一个参数的对象
                DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                //得到弹出框手机号的值
                String sens_phone1 = sens_edit_phone.getText().toString();
                //调起滴滴获得验证码的方法
                picToVerificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);
                //alertDialog.dismiss();
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完图形验证码自动跳转）
    private void picCodeinitListener() {
        mArray = new EditText[]{pice_one, pice_twe, pice_three,pice_frou};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (temp.length() == 1 && j >= 0 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }

                    if (temp.length() == 0) {
                        if (j >= 1) {
                            mArray[j - 1].setFocusable(true);
                            mArray[j - 1].setFocusableInTouchMode(true);
                            mArray[j - 1].requestFocus();
                        }
                    }
                    if (!TextUtils.isEmpty(pice_one.getText().toString().trim()) && !TextUtils.isEmpty(pice_twe.getText().toString().trim()) && !TextUtils.isEmpty(pice_three.getText().toString().trim()) && !TextUtils.isEmpty(pice_frou.getText().toString().trim())) {
                        String code = pice_one.getText().toString().trim() + pice_twe.getText().toString().trim() + pice_three.getText().toString().trim() + pice_frou.getText().toString().trim();
                        Toast.makeText(context,code, Toast.LENGTH_SHORT).show();
                        //得到一个参数的对象
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        //调起发送验证码的方法
                        setPicCodeTaskIDRequest(diDiOneParameter,"setPhotoCode","photoCode",code);
                    }
                }
            });
        }
    }


    //图形验证码之后弹出的短信验证码对话框
    private void setPicToCodeOnClick() {
        //得到一个参数的对象
        final DiDiOneParameter diDiOneParameter = new DiDiOneParameter();

        //打开获得短信验证码的对话框
        alertDialog= setAlertDialog(context, R.layout.activity_sens_pop_code);
        //得到弹出框手机号的值
        final String sens_phone1 = sens_edit_phone.getText().toString();
        //将手机号赋给验证码处
        codephone.setText("验证码发送至   "+sens_phone1);

        //滴滴输入验证码的方法
        picToCodeinitListener();

        //开启倒计时
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 70000, 1000);
        mCountDownTimerUtils.start();

        /**点击重新发送验证码**/
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了重新发送", Toast.LENGTH_SHORT).show();
                //开启倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 70000, 1000);
                mCountDownTimerUtils.start();
                //调起滴滴获得验证码的方法
                picToVerificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完验证码自动跳转）
    private void picToCodeinitListener() {
        mArray = new EditText[]{et_one, et_two, et_three,et_four};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (temp.length() == 1 && j >= 0 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }

                    if (temp.length() == 0) {
                        if (j >= 1) {
                            mArray[j - 1].setFocusable(true);
                            mArray[j - 1].setFocusableInTouchMode(true);
                            mArray[j - 1].requestFocus();
                        }
                    }
                    if (!TextUtils.isEmpty(et_one.getText().toString().trim()) && !TextUtils.isEmpty(et_two.getText().toString().trim()) && !TextUtils.isEmpty(et_three.getText().toString().trim()) && !TextUtils.isEmpty(et_four.getText().toString().trim())) {
                        String code = et_one.getText().toString().trim() + et_two.getText().toString().trim() + et_three.getText().toString().trim() + et_four.getText().toString().trim();
                        Toast.makeText(context,code, Toast.LENGTH_SHORT).show();
                        //得到一个参数的对象
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        //调起发送验证码的方法
                        submitePicTocodeTaskIDRequest(diDiOneParameter,"submitEcode","smsCode",code);
                    }
                }
            });
        }
    }


    //设置密码的点击监听方法
    private void setPopPwdOnClickPwd() {
        //打开设置密码的对话框
        alertDialog = setAlertDialog(context,R.layout.activity_sens_pop_pwd);

        //设置密码控件的监听
        sensitize_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到一个参数的对象
                DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                pwd = sens_edit_pwd.getText().toString();
                Log.d("zzz", "sensitize  设置密码之前得到密码值：" + pwd);
                //调用滴滴设置密码
                setpwdTaskIDRequest(diDiOneParameter,"setLoginPwd","password", pwd);
            }
        });
    }



    //调起得到是否注册过滴滴的TaskId的请求方法
    private void registerTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                registerDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起是否注册过滴滴的数据请求的方法
    private void registerDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(SensitizeActivity.this,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        registerDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);

                    ZhuCeBeanData zhuCeBeanData = gson.fromJson(returnJSONStr, ZhuCeBeanData.class);
                    int errorCode = zhuCeBeanData.getErrorCode();
                    String errorMessage = zhuCeBeanData.getErrorMessage();
                    if(errorCode!=0){
                        threadToast(context,errorMessage);
                    }else {
                        ZhuCeBeanData.ResultBean result = zhuCeBeanData.getResult();
                        ZhuCeBeanData.ResultBean.NameValuePairsBean nameValuePairs = result.getNameValuePairs();

                        String dataStatus = nameValuePairs.getStatus();
                        final String userType = nameValuePairs.getUserType();
                        Log.d("zzzz11", "sensitize  解析出需要的值："+dataStatus +"=="+ userType);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(userType.equals("0")){
                                    //存入值
                                    instance.saveData(context,"usetType",userType);
                                    //登录对话框的方法
                                    Pop_Log();
                                }else {
                                    //存入值
                                    instance.saveData(context,"usetType",userType);

                                    //判断如果激活过就直接弹出得到验证码
                                    DiDiOneParameter diDiOneParameter1 = new DiDiOneParameter();
                                    //得到弹出框手机号的值
                                    final String sens_phone1 = sens_edit_phone.getText().toString();
                                    verificationCodeTaskIDRequest(diDiOneParameter1,"getVerificationCode","phoneNum",sens_phone1);
                                    Log.d("zzzz12", "sensitizexxxxxx");
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    //调起得到登录滴滴的TaskId的请求方法
    private void logInTaskIDRequest(final DiDiTweParameter diDiTweParameter,String modle,String value1,String value2) {
        Call oneCall = diDiTweParameter.okUitls(modle, value1, value2);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                logInDataRequest(diDiTweParameter,taskId);
            }
        });
    }
    //调起登录滴滴的数据请求的方法
    private void logInDataRequest(final DiDiTweParameter diDiTweParameter, final String taskId) {
        Call tweCall = diDiTweParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(SensitizeActivity.this,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        logInDataRequest(diDiTweParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    DengLuBeanData dengLuBeanData = gson.fromJson(returnJSONStr, DengLuBeanData.class);
                    int errorCode = dengLuBeanData.getErrorCode();
                    String errorMessage = dengLuBeanData.getErrorMessage();
                    if(errorCode==0){
                        DengLuBeanData.ResultBean result = dengLuBeanData.getResult();
                        final String errno = result.getErrno();
                        final String error = result.getError();
                        Log.d("zzz13", "sensitize  解析出需要的值："+errno +"=="+ error);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(errno.equals("0")){
                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                    //得到token值
                                    String token = (String) instance.getData(context, "token", "");
                                    //滴滴登录成功后将滴滴信息存入到数据库
                                    Log.d("zzz14", "sensitize  得到要用的token值：" + token);
                                    presenterLayer.setSensitize("taxi",2,log_edit_pwd,log_edit_phone,token);
                                    startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
                                    finish();
                                }else if(errno.equals("-425")){
                                    threadToast(context,error);
                                    //调起获取验证码的接口
                                    DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                                    //得到弹出框手机号的值
                                    final String sens_phone1 = sens_edit_phone.getText().toString();
                                    //调起滴滴获得验证码的方法
                                    picToVerificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);
                                }else {
                                    Toast.makeText(context, "登录失败：" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        threadToast(context,errorMessage);
                    }
                }
            }
        });
    }



    //调起得到获取验证码滴滴的 TaskId的请求方法
    private void verificationCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                verificationCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起获取验证码滴滴的求数据请的方法
    private void verificationCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(context,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        picToVerificationCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    GetPicCodeBeanData getPicCodeBeanData = gson.fromJson(returnJSONStr, GetPicCodeBeanData.class);
                    int errorCode = getPicCodeBeanData.errorCode;
                    String errorMessage = getPicCodeBeanData.errorMessage;
                    Log.d("zzz15", "得到验证码之类的值：" + errorMessage + "==" + errorCode);
                    if(errorCode!=0){
                        threadToast(context,errorMessage);
                    }else {
                        GetPicCodeBeanData.ResultBean result = getPicCodeBeanData.result;
                        List<Object> values = result.values;
                        if(values==null){
                            threadToast(context,"获得短信验证码");
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setCodeOnClick();
                                }
                            });
                            Log.d("zzzz16", "sensitizexxxxxx");
                        }else {
                            Object msg = values.get(0);
                            final Object pic= values.get(1);
                            Log.d("zzz17", "得到验证码之类的值："+msg+"=="+pic);
                            DiDiCodeBean diDiCodeBean = gson.fromJson(String.valueOf(msg), DiDiCodeBean.class);
                            String errno = diDiCodeBean.getErrno();
                            String error = diDiCodeBean.getError();
                            Log.d("zzz18", "得到验证码之类的值集合0里面的值："+errno+ "==" + error);
                            if(errno.equals("1003")){
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPhotoCodeOnClick(pic);
                                    }
                                });
                            }else {
                                threadToast(context,error);
                            }
                        }
                    }
                }
            }
        });
    }

    //调起得到发送图形验证码 滴滴的TaskId的请求方法
    private void setPhotoCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                setPhotoCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起发送图形验证码滴滴的数据请求的方法
    private void setPhotoCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(context,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        setPhotoCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                GetPicCodeBeanData getPicCodeBeanData = gson.fromJson(returnJSONStr, GetPicCodeBeanData.class);
                int errorCode = getPicCodeBeanData.errorCode;
                String errorMessage = getPicCodeBeanData.errorMessage;
                Log.d("zzz19", "得到验证码之类的值：" + errorMessage + "==" + errorCode);
                if(errorCode!=0){
                    threadToast(context,errorMessage);
                }else {
                    GetPicCodeBeanData.ResultBean result = getPicCodeBeanData.result;
                    List<Object> values = result.values;
                    if(values==null){
                        threadToast(context,"验证成功");
                        HuoQuYanZhengMaBeanData huoQuYanZhengMaBeanData = gson.fromJson(returnJSONStr, HuoQuYanZhengMaBeanData.class);
                        HuoQuYanZhengMaBeanData.ResultBean result1 = huoQuYanZhengMaBeanData.getResult();
                        String errno = result1.getErrno();
                        String error = result1.getError();
                        Log.d("zzz101", "得到验证码之类的值："+errno+ "==" +error);
                        if(errno.equals("0")){
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setCodeOnClick();
                                }
                            });
                            alertDialog.cancel();
                        }else {
                            threadToast(context,"图形验证码的信息："+error);
                        }
                    }else {
                        Object msg = values.get(0);
                        final Object pic= values.get(1);
                        Log.d("zzz102", "得到验证码之类的值："+msg+ "==" +pic);
                        DiDiCodeBean diDiCodeBean = gson.fromJson(String.valueOf(msg), DiDiCodeBean.class);
                        String errno = diDiCodeBean.getErrno();
                        String error = diDiCodeBean.getError();
                        Log.d("zzz103", "得到验证码之类的值集合0里面的值："+errno+ "==" + error);
                        if(errno.equals("2002")){
                            alertDialog.cancel();
                            threadToast(context,error);
                            Log.d("zzz104", "得到验证错误后的图形：" +pic);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPhotoCodeOnClick(pic);
                                }
                            });
                        }else {
                            threadToast(context,error);
                        }
                    }
                }
            }
        });
    }

    //调起得到发送验证码滴滴的TaskId的请求方法
    private void submiteCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                submiteCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起发送验证码滴滴的数据请求的方法
    private void submiteCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(SensitizeActivity.this,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        submiteCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    if(returnJSONStr.equals("none")){
                        return;
                    }else {
                        //得到最终要解析的值
                        DiDiCodeBean diDiCodeBean = gson.fromJson(returnJSONStr, DiDiCodeBean.class);
                        final String errno = diDiCodeBean.getErrno();
                        final String error = diDiCodeBean.getError();
                        Log.d("zzzz105", "sensitize  解析出需要的值："+errno +"=="+ error);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"没有手机号注册：", Toast.LENGTH_SHORT).show();
                                if(error.equals("OK")){
                                    setPopPwdOnClickPwd();
                                }else {
                                    Toast.makeText(context,"没有手机号注册：" +error, Toast.LENGTH_SHORT).show();
                                    setPopPwdOnClickPwd();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    //调起得到设置密码 滴滴的TaskId的请求方法
    private void setpwdTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                setpwdDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起设置密码 滴滴的数据请求的方法
    private void setpwdDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(SensitizeActivity.this,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        setpwdDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    SheZhiMiMaBeanData sheZhiMiMaBeanData = gson.fromJson(returnJSONStr, SheZhiMiMaBeanData.class);
                    int errorCode = sheZhiMiMaBeanData.getErrorCode();
                    String errorMessage = sheZhiMiMaBeanData.getErrorMessage();
                    if(errorCode!=0){
                        threadToast(context,errorMessage);
                    }else {
                        String result = sheZhiMiMaBeanData.getResult();
                        Log.d("zzz", "终于到了result:" + result);
                        SheZhiMiMaSuccessBeanData sheZhiMiMaSuccessBeanData = gson.fromJson(result, SheZhiMiMaSuccessBeanData.class);
                        final String errno = sheZhiMiMaSuccessBeanData.getErrno();
                        final String error = sheZhiMiMaSuccessBeanData.getError();
                        Log.d("zzz", "终于到了设置密码想要的值了：" + errno+"==="+error);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(errno.equals("0")){
                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                    //得到token值
                                    String token = (String) instance.getData(context, "token", "");
                                    //滴滴登录成功后将滴滴信息存入到数据库
                                    Log.d("zzz", "sensitize  得到要用的token值：" + token);
                                    presenterLayer.setSensitize("taxi",2,pwd,sens_phone,token);
                                    Log.d("zzz", "  设置密码之前得到密码值：" + pwd);
                                    startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(context, "设置密码的提示：\n"+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }




    //调起得到获取验证码滴滴的 TaskId的请求方法
    private void picToVerificationCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                picToVerificationCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起获取验证码滴滴的求数据请的方法
    private void picToVerificationCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(context,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        picToVerificationCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    GetPicCodeBeanData getPicCodeBeanData = gson.fromJson(returnJSONStr, GetPicCodeBeanData.class);
                    int errorCode = getPicCodeBeanData.errorCode;
                    String errorMessage = getPicCodeBeanData.errorMessage;
                    Log.d("zzz107", "得到验证码之类的值：" + errorMessage + "==" + errorCode);
                    if(errorCode!=0){
                        threadToast(context,errorMessage);
                    }else {
                        GetPicCodeBeanData.ResultBean result = getPicCodeBeanData.result;
                        List<Object> values = result.values;
                        if(values==null){
                            Log.d("zzz108", "fffsffdfs");
                            threadToast(context,"获得短信验证码");
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPicToCodeOnClick();
                                }
                            });
                        }else {
                            Object msg = values.get(0);
                            final Object pic= values.get(1);
                            Log.d("zzz109", "得到验证码之类的值："+msg+"=="+pic);
                            DiDiCodeBean diDiCodeBean = gson.fromJson(String.valueOf(msg), DiDiCodeBean.class);
                            String errno = diDiCodeBean.getErrno();
                            String error = diDiCodeBean.getError();
                            Log.d("zzz110", "得到验证码之类的值集合0里面的值："+errno+ "==" + error);
                            if(errno.equals("1003")){
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPicCodeOnClick(pic);
                                    }
                                });
                            }else {
                                threadToast(context,error);
                            }
                        }
                    }
                }
            }
        });
    }

    //调起得到发送图形验证码 滴滴的TaskId的请求方法
    private void setPicCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                setPicCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起发送图形验证码滴滴的数据请求的方法
    private void setPicCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(context,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        setPicCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                GetPicCodeBeanData getPicCodeBeanData = gson.fromJson(returnJSONStr, GetPicCodeBeanData.class);
                int errorCode = getPicCodeBeanData.errorCode;
                String errorMessage = getPicCodeBeanData.errorMessage;
                Log.d("zzz", "得到验证码之类的值：" + errorMessage + "==" + errorCode);
                if(errorCode!=0){
                    threadToast(context,errorMessage);
                }else {
                    GetPicCodeBeanData.ResultBean result = getPicCodeBeanData.result;
                    List<Object> values = result.values;
                    if(values==null){
                        threadToast(context,"验证成功");
                        HuoQuYanZhengMaBeanData huoQuYanZhengMaBeanData = gson.fromJson(returnJSONStr, HuoQuYanZhengMaBeanData.class);
                        HuoQuYanZhengMaBeanData.ResultBean result1 = huoQuYanZhengMaBeanData.getResult();
                        String errno = result1.getErrno();
                        String error = result1.getError();
                        Log.d("zzz111", "得到验证码之类的值："+errno+ "==" +error);
                        if(errno.equals("0")){
                            alertDialog.cancel();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPicToCodeOnClick();
                                }
                            });
                        }else {
                            threadToast(context,"图形验证码的信息："+error);
                        }
                    }else {
                        Object msg = values.get(0);
                        final Object pic= values.get(1);
                        Log.d("zzz112", "得到验证码之类的值："+msg+ "==" +pic);
                        DiDiCodeBean diDiCodeBean = gson.fromJson(String.valueOf(msg), DiDiCodeBean.class);
                        String errno = diDiCodeBean.getErrno();
                        String error = diDiCodeBean.getError();
                        Log.d("zzz", "得到验证码之类的值集合0里面的值："+errno+ "==" + error);
                        if(errno.equals("2002")){
                            alertDialog.cancel();
                            threadToast(context,error);
                            Log.d("zzz113", "得到验证错误后的图形：" +pic);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPicCodeOnClick(pic);
                                }
                            });
                        }else {
                            threadToast(context,error);
                        }
                    }
                }
            }
        });
    }

    //图片验证码发送成功后，发送短信验证码    滴滴的TaskId的请求方法
    private void submitePicTocodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(context);
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
                submitePicTocodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //图片验证码发送成功后，发送短信验证码    滴滴的数据请求的方法
    private void submitePicTocodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(SensitizeActivity.this,"连接超时，请重试");
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
                            Toast.makeText(context, "滴滴没有开启", Toast.LENGTH_SHORT).show();
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
                        submitePicTocodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    if(returnJSONStr.equals("none")){
                        return;
                    }else {
                        //得到最终要解析的值
                        DiDiCodeBean diDiCodeBean = gson.fromJson(returnJSONStr, DiDiCodeBean.class);
                        final String errno = diDiCodeBean.getErrno();
                        final String error = diDiCodeBean.getError();
                        Log.d("zzz114", "sensitize  解析出需要的值："+errno +"=="+ error);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(errno.equals("0")){
                                    /**
                                     * 只是猜测是指样可以（待修改）
                                     *
                                     * **/
                                    String  usetType = (String) instance.getData(context, "usetType", "-1");
                                    Log.d("zzz", "查看是不是没有注册过的手机：" + usetType);
                                    if(usetType.equals("1")){
                                        Log.d("zzz115", "sensitize sffafdfda ");
                                        setPopPwdOnClickPwd();
                                    }else {
                                        Log.d("zzz120", "sensitize 思路没问题"+usetType);
                                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                        alertDialog.cancel();
                                        presenterLayer.setSensitize("taxi",2,log_edit_pwd,log_edit_phone,token);
                                        startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
                                        finish();
                                    }
                                }else {
                                    String  usetType = (String) instance.getData(context, "usetType", "-1");
                                    Log.d("zzz", "查看是不是没有注册过的手机：" + usetType);
                                    Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }



    //弹出对话框的属性
    public AlertDialog setAlertDialog(Context context,int view){
        //得到对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //得到对话框的对象
        AlertDialog alertDialog = builder.create();
        //得到要加载的布局
        View alertView = View.inflate(context, view, null);
        //得到对话框里的控件
        setCheck(alertView);
        //设置点击不消失
        alertDialog.setCanceledOnTouchOutside(false);
        //将布局设置到对话框
        alertDialog.setView(alertView);
        //显示对话框
        alertDialog.show();
        return alertDialog;
    }
    //得到弹出框的控件
    private void setCheck(View view){
        sensitize_car = (TextView) findViewById(R.id.sensitize_car);
        codephone = (TextView) view.findViewById(R.id.codephone);
        //得到调到设置密码的控件
        resend = (TextView) view.findViewById(R.id.resend);
        //设置的密码控件
        sens_edit_pwd = (EditText) view.findViewById(R.id.sens_edit_pwd);
        //得到设置密码的控件
        sensitize_pwd = (TextView) view.findViewById(R.id.sensitize_pwd);
        //得到登录的控件
        sens_log = (Button) view.findViewById(R.id.sens_log);
        //得到登录手机号控件
        sens_log_edit_phone = (EditText) view.findViewById(R.id.sens_log_edit_phone);
        //得到登录密码的控件
        sens_log_edit_pwd = (EditText) view.findViewById(R.id.sens_log_edit_pwd);
        //得到忘记密码的控件
        sens_forget = (TextView) view.findViewById(R.id.sens_forget);
        //将要跳到验证码的控件
        pic_code = (ImageView) view.findViewById(R.id.pic_code);
        //得到输入图形验证码的控件
        pice_one = (EditText) view.findViewById(R.id.pice_one);
        pice_twe = (EditText) view.findViewById(R.id.pice_twe);
        pice_three = (EditText) view.findViewById(R.id.pice_three);
        pice_frou = (EditText) view.findViewById(R.id.pice_frou);
        //PopupWindow的布局控件
        et_one = (EditText) view.findViewById(R.id.e_one);
        et_two = (EditText) view.findViewById(R.id.e_twe);
        et_three = (EditText) view.findViewById(R.id.e_three);
        et_four = (EditText) view.findViewById(R.id.e_frou);
    }

    //将16进制改成btye数组
    private byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    //判断手机号的正则标的是
    public  boolean isMobile(String mobile) {
        return Pattern.matches( "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$", mobile);
    }

    //是否激活了本App
    @Override
    public void status(StatusBean statusBean) {
        int code = statusBean.getCode();
        int data = statusBean.getData();
        String msg = statusBean.getMsg();
        String msg_en = statusBean.getMsg_en();
        if(code==1){
            Log.d("zzz", "sensitize  返回的信息"+code+"\n"+ msg + "\n" + msg_en);
            Toast.makeText(context, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            if(data==1){
                //下一步的点击监听
                nextOnclivk();
            }else {
                //弹出的对话框-
                normalDialog(data);
                //下一步的点击监听
                //nextOnclivk();
                Log.d("zzz", "sensitize  返回的信息   请去邮箱激活此APP，方可激活服务");
            }
        }else {
            Log.d("zzz", "sensitize  返回的信息"+code+"\n"+ msg + "\n" + msg_en);
            Toast.makeText(context, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }
    //重新发送激活邮件
    @Override
    public void reActiveData(ReActiveUserBean reActiveUserBean) {
        int code = reActiveUserBean.getCode();
        String msg = reActiveUserBean.getMsg();
        String msg_en = reActiveUserBean.getMsg_en();
        Log.d("zzz", "sensitize 重新激活的链接的情况："+code+"\n" + msg + "\n" + msg_en);
        if(code==1){
            Toast.makeText(this,msg+"\n "+msg_en, Toast.LENGTH_SHORT).show();
            //开启倒计时
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(chongxin, 60000, 1000);
            mCountDownTimerUtils.start();
        }else {
            Toast.makeText(this, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }
    //得到激活服务的信息
    @Override
    public void getSensitize(SensitizeBean sensitizeBean) {
        Log.d("zzz", "sensitize  这里已经通过了");
        int code = sensitizeBean.getCode();
        String msg = sensitizeBean.getMsg();
        String msg_en = sensitizeBean.getMsg_en();
        Log.d("zzz", "sensitize  储存激活服务的状态信息:"+code+"=="+msg+"--"+msg_en);
    }
    //弹出的对话框
    private void normalDialog(final int data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alertDialog= builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        View view = View.inflate(context, R.layout.activity_alertdialog, null);
        TextView ok=(TextView) view.findViewById(R.id.ok);
        TextView quxiao=(TextView) view.findViewById(R.id.quxiao);
        chongxin = (TextView) view.findViewById(R.id.chongxin);
        //确定按钮
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, R.string.adl_tweMsg_t, Toast.LENGTH_LONG).show();
                startActivity(new Intent(SensitizeActivity.this, MainActivity.class));
                alertDialog.cancel();
                SensitizeActivity.this.finish();
            }
        });
        chongxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zzz", "sensitize 得到token" + token);
                //调起得到重新激活邮件
                presenterLayer.setreActive(token);
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    //子线程吐司的方法
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
