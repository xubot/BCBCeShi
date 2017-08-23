package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.StatusBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.SensitizeView;

public class SensitizeActivity extends AppCompatActivity implements SensitizeView{
    private PopupWindow popupWindow;
    private TextView codephone;
    private Button sensitize_s;
    private TextView sensitize_pwd;
    private Context context=SensitizeActivity.this;
    private Button sens_log;
    private TextView sensitize_car;
    private TextView sens_forget;
    private ImageView pic_code;
    private EditText sens_edit_phone;
    private EditText sens_log_edit_phone;
    private PresenterLayer presenterLayer;
    private SharedUtils instance;
    private String logintoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitize);
        //得到注册的token值
        instance = SharedUtils.getInstance();
        int code = (int) instance.getData(this, "code", 0);
        if(code==1){
            LinearLayout back= (LinearLayout) findViewById(R.id.sensitizze_back);
            ImageView close= (ImageView) findViewById(R.id.sensitize_close);
            sensitize_s = (Button) findViewById(R.id.sensitize_s);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SensitizeActivity.this,SensitizeActivity.class));
                    finish();
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            sensitize_s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sens_edit_phone = (EditText)findViewById(R.id.sens_edit_phone);
                    //得到弹出框的值
                    String sens_phone = sens_edit_phone.getText().toString();
                    View view_log = View.inflate(context, R.layout.activity_sens_pop_log, null);
                    //得到弹出框的控件
                    setCheck(view_log);
                    //设置弹出框
                    setPopupWindow(view_log, 750);
                    //给登录弹出框赋值
                    sens_log_edit_phone.setText(sens_phone);
                    //设置显示 弹出框
                    setShowPopup(view_log);
                    //点击忘记密码弹出的对话框
                    setPopForgetOnClick();
                    //账号密码正确是立即进入
                    setPopLogOnClickLog();
                    //判断如果激活过就直接弹出得到验证码
                    //setCodeOnClick();
                }
            });
            sensitize_car = (TextView) findViewById(R.id.sensitize_car);
            //得到token值
            logintoken = (String) instance.getData(this, "token", "");
            Log.d("zzz", "logintoken=" + logintoken);
            //开启请求
            load();
        }else {
            startActivity(new Intent(SensitizeActivity.this,LogActivity.class));
            this.finish();
        }
    }

    public void load(){
        Log.d("zzz", "logintokenload=" + logintoken);
        presenterLayer = new PresenterLayer();
        presenterLayer.SensitizeView(this);
        presenterLayer.setStatus(logintoken);
    }


    //判断如果激活过就直接弹出得到验证码
    private void setCodeOnClick() {
        //设置取消弹出框
        setPopDis();
        //得到要弹出的布局
        View view_code = View.inflate(context, R.layout.activity_sens_pop_code, null);
        //得到弹出框的控件
        setCheck(view_code);
        //设置弹出框
        setPopupWindow(view_code, 650);
        //设置显示 弹出框
        setShowPopup(view_code);

        codephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹出框
                setPopDis();
            }
        });
    }
    //点击忘记密码的监听
    private void setPopForgetOnClick() {
        sens_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹出框
                setPopDis();
                View view_piccode = View.inflate(context, R.layout.activity_sens_pop_pic_code, null);
                //得到弹出框的控件
                setCheck(view_piccode);
                //设置弹出框
                setPopupWindow(view_piccode, 700);
                //设置显示 弹出框
                setShowPopup(view_piccode);
                //设置图形验证码的监听
                setPopPicCodeOnClick();
            }
        });
    }
    //设置图形验证码的监听
    private void setPopPicCodeOnClick() {
        pic_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹出框
                setPopDis();
                //得到要弹出的布局
                View view_code = View.inflate(context, R.layout.activity_sens_pop_code, null);
                //得到弹出框的控件
                setCheck(view_code);
                //设置弹出框
                setPopupWindow(view_code, 650);
                //设置显示 弹出框
                setShowPopup(view_code);
                //设置验证码的监听
                setPopCodeOnClick();
            }
        });
    }

    //设置验证码的监听
    private void setPopCodeOnClick() {
        //输入验证码后的点击监听
        codephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹出框
                setPopDis();
                //得到要弹出的布局
                View view_pwd = View.inflate(context, R.layout.activity_sens_pop_pwd, null);
                //得到弹出框的控件
                setCheck(view_pwd);
                //设置弹出框
                setPopupWindow(view_pwd, 700);
                //设置显示 弹出框
                setShowPopup(view_pwd);
                //设置密码的点击监听
                setPopPwdOnClickPwd();
            }
        });
    }
    //设置密码的点击监听
    private void setPopPwdOnClickPwd() {
        //设置完密码的监听
        sensitize_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹出框
                setPopDis();
            }
        });
    }
    //账号密码正确是立即进入
    private void setPopLogOnClickLog() {
        //得到登录的控件
        sens_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹出框
                setPopDis();
            }
        });
    }
    //得到弹出框的控件
    private void setCheck(View view){
        //得到调到设置密码的控件
        codephone = (TextView) view.findViewById(R.id.codephone);
        //得到设置密码的控件
        sensitize_pwd = (TextView) view.findViewById(R.id.sensitize_pwd);
        //得到登录的控件
        sens_log = (Button) view.findViewById(R.id.sens_log);
        sens_log_edit_phone = (EditText) view.findViewById(R.id.sens_log_edit_phone);
        //得到忘记密码的控件
        sens_forget = (TextView) view.findViewById(R.id.sens_forget);
        //将要跳到验证码的控件
        pic_code = (ImageView) view.findViewById(R.id.pic_code);

    }
    //设置显示弹出框
    private void setShowPopup(View view) {
        popupWindow.showAtLocation(view, Gravity.CENTER,-0,100);
    }
    //设置移除弹出框
    private void setPopDis() {
        popupWindow.dismiss();
    }
    //设置弹出框
    private void setPopupWindow(View view, int height) {
        //得到popupWindow,设置popupWindow显示的内容和宽高
        popupWindow = new PopupWindow(view, 900, height);
        //设置popupWindow的背景,当前设置的是透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置点击popupWindow外部,是否可以消失,如果想要消失,必须通过setBackgroundDrawable方法设置popupWindow的背景
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
    }
    //是否激活了本App
    @Override
    public void status(StatusBean statusBean) {
        int code = statusBean.getCode();
        int data = statusBean.getData();
        String msg = statusBean.getMsg();
        String msg_en = statusBean.getMsg_en();
        if(code==1){
            Log.d("zzz", "sensitize=" + msg + "\n" + msg_en);
            Toast.makeText(context, "本APP是否激活（1：是；0：否）：\n"+data, Toast.LENGTH_SHORT).show();
            if(data==1){
                //打开弹出框点击监听
                //setSensOnClickCar();
            }else {
                Toast.makeText(context, "请去邮箱激活此APP，方可激活服务", Toast.LENGTH_SHORT).show();
            }
        }else {
            Log.d("zzz", "sensitize=" + msg + "\n" + msg_en);
            Toast.makeText(context, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }
    //是否注册过滴滴账号
    @Override
    public void ifRegister() {

    }
}
