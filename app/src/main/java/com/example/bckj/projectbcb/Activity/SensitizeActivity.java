package com.example.bckj.projectbcb.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.DiDiBean.CodeSuccessBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiCodeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiCodeOrPicBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiTaskIdBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiZhuCeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiZhuCeDataBean;
import com.example.bckj.projectbcb.Bean.ReActiveUserBean;
import com.example.bckj.projectbcb.Bean.SensitizeBean;
import com.example.bckj.projectbcb.Bean.StatusBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.CountDownTimerUtils;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiOneParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiTweParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.PathUrl;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.SensitizeView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class SensitizeActivity extends AppCompatActivity implements SensitizeView{
    private TextView resend;
    private Button sensitize_s;
    private TextView sensitize_pwd;
    private Context context=SensitizeActivity.this;
    private Button sens_log;
    private TextView sensitize_car;
    private TextView sens_forget;
    private ImageView pic_code;
    private EditText sens_edit_phone;
    private EditText sens_log_edit_phone;
    private PresenterLayer presenterLayer = new PresenterLayer();
    private SharedUtils instance;
    private String token;
    private String sens_phone;
    private String log_edit_phone;
    private EditText sens_log_edit_pwd;
    private String log_edit_pwd;
    private EditText et_one;
    private EditText et_two;
    private EditText et_three;
    private EditText et_four;
    private AlertDialog alertDialog;
    private TextView chongxin;
    private EditText pice_one;
    private EditText pice_twe;
    private EditText pice_three;
    private EditText pice_frou;
    private EditText sens_edit_pwd;


    //判断用户是否注册个handler对象
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            //取出消息内容
            Object what = msg.obj;
            Log.d("zzz1", (String) what);
            if(what.equals("0")){
                //登录对话框的方法
                Pop_Log();
            }else {
                //判断如果激活过就直接弹出得到验证码
                setCodeOnClick();
            }
        }
    };
    //登录handler对象
    Handler logHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            //取出消息内容
            Object what = msg.obj;
            Log.d("zzz", "sensitize  滴滴登录传来的状态："+ what);
            if(what.equals("success")) {
                instance.saveData(context,"success",what);
                Toast.makeText(context, "sensitize 登录成功："+what, Toast.LENGTH_SHORT).show();
                //消失的方法

                //得到token值
                String token = (String) instance.getData(context, "token", "");
                //滴滴登录成功后将滴滴信息存入到数据库
                Log.d("zzz", "sensitize  得到要用的token值：" + token);
                presenterLayer.setSensitize("taxi",2,log_edit_pwd,log_edit_phone,token);
                finish();
            }
        }
    };

    //发送验证码正确的handler对象
    Handler codeHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            //取出消息内容
            Object what = msg.obj;
            Log.d("zzz1", (String) what);
            if(what.equals("success")) {
                //打开设置密码的对话框
                final AlertDialog alertDialog = setAlertDialog(context,R.layout.activity_sens_pop_pwd);

                //设置密码控件的监听
                sensitize_pwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPopPwdOnClickPwd();
                        alertDialog.cancel();
                    }
                });
            }
        }
    };

    //发送验证码错误的handler对象
    Handler codeHandler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //取出消息内容
            Object what = msg.obj;
            Log.d("zzz", "sensitize  滴滴登录传来的状态："+ what);
            if(what.equals("-104")){
                Toast.makeText(context, "验证码输入格式有误", Toast.LENGTH_LONG).show();
            }else if(what.equals("-302")){
                Toast.makeText(context, "请输入正确的验证码", Toast.LENGTH_LONG).show();
            }else if(what.equals("-301")){
                Toast.makeText(context, "验证码已过期，请重新获取", Toast.LENGTH_LONG).show();
            }
        }
    };

    //发送图片code  handler对象
    Handler picCodeHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            //取出消息内容
            Object what = msg.obj;
            Log.d("zzz1", (String) what);
            if(what.equals("success")) {
                setCodeOnClick();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitize);
        PathUrl.getIp(context);
        //得到注册的token值
        instance = SharedUtils.getInstance();
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
                    startActivity(new Intent(context,MainActivity.class));
                    finish();
                }
            });
            //关闭控件的点击监听
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        sensitize_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到一个参数的对象
                DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                //得到手机号码的输入框
                sens_edit_phone = (EditText)findViewById(R.id.sens_edit_phone);
                //得到手机号的值
                sens_phone = sens_edit_phone.getText().toString();
                //调用滴滴的接口方法
                registerTaskIDRequest(diDiOneParameter,"getRegisterStatus","phoneNum", sens_phone);
            }
        });
    }

    //登录的对话框
    private void Pop_Log() {
        //打开登录的对话框
        final AlertDialog alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_log);
        //得到输入手机号的控件
        sens_edit_phone = (EditText)findViewById(R.id.sens_edit_phone);
        //得到手机号的值
        String sens_phone = sens_edit_phone.getText().toString();
        //给登录的对话框电话处赋值
        sens_log_edit_phone.setText(sens_phone);
        //对话框里登录的监听
        sens_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopLogOnClickLog();
                finish();
            }
        });
        //对话框里忘记密码吗的监听
        sens_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopForgetOnClick();
                alertDialog.cancel();
            }
        });
    }

    //短信验证码对话框（没有注册过的手机号）
    private void setCodeOnClick() {
        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();

        //打开获得短信验证码的对话框
        final AlertDialog alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_code);
        //得到弹出框手机号的值
        String sens_phone1 = sens_edit_phone.getText().toString();
        //调起滴滴获得验证码的方法
        verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);

        //设置EditText的文本改变的监听
        et_one.addTextChangedListener(textWatcher);
        et_two.addTextChangedListener(textWatcher);
        et_three.addTextChangedListener(textWatcher);
        et_four.addTextChangedListener(textWatcher);

        //开启倒计时
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 60000, 1000);
        mCountDownTimerUtils.start();

        /**点击重新发送验证码**/
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了重新发送", Toast.LENGTH_SHORT).show();
                //开启倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 60000, 1000);
                mCountDownTimerUtils.start();
                //重新发送验证码的方法
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完验证码自动跳转）
    private TextWatcher textWatcher = new TextWatcher() {
        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        @Override//文本改变之前的方法
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override//改变中
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override//改变后
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == 1) {//editable.toString()为当前输入框的内容，限定长度为1
                if (et_one.isFocused()) {
                    et_one.clearFocus();
                    et_two.requestFocus();
                } else if (et_two.isFocused()) {
                    et_two.clearFocus();
                    et_three.requestFocus();
                } else if (et_three.isFocused()) {
                    et_three.clearFocus();
                    et_four.requestFocus();
                } else if (et_four.isFocused()) {
                    et_four.clearFocus();
                    String one = et_one.getText().toString();
                    String twe = et_two.getText().toString();
                    String three = et_three.getText().toString();
                    String four = et_four.getText().toString();
                    String code = one + twe + three + four;
                    Toast.makeText(context, "验证码："+one+twe+three+four, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "已经输入完毕", Toast.LENGTH_SHORT).show();
                    //调起发送验证码的方法
                    submitecodeTaskIDRequest(diDiOneParameter,"submitEcode","SmsCode",code);
                }
            }
        }
    };

    //点击忘记密码的监听（图形验证码的对话框）
    private void setPopForgetOnClick() {
        //打开获得图形验证码的对话框
        final AlertDialog alertDialog = setAlertDialog(context, R.layout.activity_sens_pop_pic_code);
        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        //得到弹出框手机号的值
        String sens_phone1 = sens_edit_phone.getText().toString();
        //调起滴滴获得验证码的方法
        verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",sens_phone1);

        //设置EditText的文本改变的监听
        pice_one.addTextChangedListener(picTextWatcher);
        pice_twe.addTextChangedListener(picTextWatcher);
        pice_three.addTextChangedListener(picTextWatcher);
        pice_frou.addTextChangedListener(picTextWatcher);
        /**点击图片换一张**/
        pic_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了重新换一张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完图形验证码自动跳转）
    private TextWatcher picTextWatcher = new TextWatcher() {
        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        @Override//文本改变之前的方法
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override//改变中
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override//改变后
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == 1) {//editable.toString()为当前输入框的内容，限定长度为1
                if (pice_one.isFocused()) {
                    pice_one.clearFocus();
                    pice_twe.requestFocus();
                } else if (pice_twe.isFocused()) {
                    pice_twe.clearFocus();
                    pice_three.requestFocus();
                } else if (pice_three.isFocused()) {
                    pice_three.clearFocus();
                    pice_frou.requestFocus();
                } else if (pice_frou.isFocused()) {
                    pice_frou.clearFocus();
                    String one = pice_one.getText().toString();
                    String twe = pice_twe.getText().toString();
                    String three = pice_three.getText().toString();
                    String four = pice_frou.getText().toString();
                    String code = one + twe + three + four;
                    Toast.makeText(context, "验证码："+one+twe+three+four, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "已经输入完毕", Toast.LENGTH_SHORT).show();
                    //调起发送验证码的方法
                    setPhotoCodeTaskIDRequest(diDiOneParameter,"setPhotoCode","photoCode",code);
                }
            }
        }
    };

    //设置密码的点击监听方法
    private void setPopPwdOnClickPwd() {
        //得到一个参数的对象
        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
        String pwd = sens_edit_pwd.getText().toString();
        Log.d("zzz", "sensitize  设置密码之前得到密码值：" + pwd);
        //调用滴滴设置密码
        setpwdTaskIDRequest(diDiOneParameter,"setLoginPwd","password",pwd);
    }

    //账号密码正确是立即进入的监听事件
    private void setPopLogOnClickLog() {
        //得到两个参数的对象
        DiDiTweParameter diDiTweParameter = new DiDiTweParameter();
        //得到输入控上的用户名
        log_edit_phone = sens_log_edit_phone.getText().toString();
        //得到输入框上的密码
        log_edit_pwd = sens_log_edit_pwd.getText().toString();
        Log.d("zzz", "sensitize  登录的手机号和密码："+log_edit_phone +"==="+ log_edit_pwd);
        //调用登录滴滴的方法
        logInTaskIDRequest(diDiTweParameter,"login",log_edit_phone,log_edit_pwd);
    }




    //调起得到是否注册过滴滴的TaskId的请求方法
    private void registerTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                Toast.makeText(context, "找不到网关地址", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                DiDiZhuCeDataBean data1Bean = gson.fromJson(returnJSONStr, DiDiZhuCeDataBean.class);
                DiDiZhuCeDataBean.NameValuePairsBean nameValuePairs = data1Bean.getNameValuePairs();
                String dataStatus = nameValuePairs.getStatus();
                String userType = nameValuePairs.getUserType();
                Log.d("zzzz", "sensitize  解析出需要的值："+dataStatus +"=="+ userType);
                Message obtain = Message.obtain();
                obtain.obj=userType;
                handler.sendMessage(obtain);
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
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                Toast.makeText(context, "找不到网关地址", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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
                        verificationCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                DiDiCodeOrPicBean diDiCodeOrPicBean = gson.fromJson(returnJSONStr, DiDiCodeOrPicBean.class);
                List<String> values = diDiCodeOrPicBean.getValues();
                Log.d("zzz", "终于values:" + values);
                if(values!=null){
                    //解析的到图形验证码 加载图形验证码的布局
                    Log.d("zzz", "图像验证码有问题");
                }else {
                    //短信验证码
                    CodeSuccessBean codeSuccessBean = gson.fromJson(returnJSONStr, CodeSuccessBean.class);
                    String errno = codeSuccessBean.getErrno();
                    String error = codeSuccessBean.getError();
                    Log.d("zzz", "得到短信验证码的状态：" + errno+"=="+error);
                }
            }
        });
    }

    //调起得到发送验证码滴滴的TaskId的请求方法
    private void submitecodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                Toast.makeText(context, "找不到网关地址", Toast.LENGTH_SHORT).show();
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
                submitecodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起发送验证码滴滴的数据请求的方法
    private void submitecodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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
                        submitecodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);

                if(returnJSONStr.equals("success")){
                    Message obtain = Message.obtain();
                    obtain.obj=returnJSONStr;
                    codeHandler.sendMessage(obtain);
                }else {
                    //得到最终要解析的值
                    DiDiCodeBean diDiCodeBean = gson.fromJson(returnJSONStr, DiDiCodeBean.class);
                    String errno = diDiCodeBean.getErrno();
                    String error = diDiCodeBean.getError();
                    Log.d("zzzz", "sensitize  解析出需要的值："+errno +"=="+ error);
                    Message obtain = Message.obtain();
                    obtain.obj=errno;
                    codeHandler1.sendMessage(obtain);
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
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                Toast.makeText(context, "找不到网关地址", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                if(returnJSONStr.equals("success")){
                    Message obtain = Message.obtain();
                    obtain.obj=returnJSONStr;
                    logHandler.sendMessage(obtain);
                }else {
                    //错误的信息
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
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                Toast.makeText(context, "找不到网关地址", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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

                Message obtain = Message.obtain();
                obtain.obj="success";
                picCodeHandler.sendMessage(obtain);

                //得到成功的标示跳到设置密码页面
                /*if(returnJSONStr.equals("success")){
                    Message obtain = Message.obtain();
                    obtain.obj="success";
                    picCodeHandler.sendMessage(obtain);
                }else {
                    //得到最终要解析的值
                    DiDiCodeBean diDiCodeBean = gson.fromJson(returnJSONStr, DiDiCodeBean.class);
                    String errno = diDiCodeBean.getErrno();
                    String error = diDiCodeBean.getError();
                    Log.d("zzzz", "sensitize  解析出需要的值："+errno +"=="+ error);
                    Message obtain = Message.obtain();
                    obtain.obj=errno;
                    codeHandler1.sendMessage(obtain);
                }*/
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
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                Toast.makeText(context, "找不到网关地址", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                //成功之后登录
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
        alertDialog = builder.create();
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
                //startActivity(new Intent(SensitizeActivity.this, MainActivity.class));
                SensitizeActivity.this.finish();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,R.string.adl_tweMsg_t,Toast.LENGTH_LONG).show();
                startActivity(new Intent(SensitizeActivity.this,MainActivity.class));
                finish();
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
}
