package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.bckj.projectbcb.R;

public class SensitizeActivity extends AppCompatActivity {
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
    private View view_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitize);
        sensitize_car = (TextView) findViewById(R.id.sensitize_car);
        //打开弹出框点击监听
        setSensOnClickCar();
    }
    //打开弹出框点击监听
    private void setSensOnClickCar() {
        //点击打车时弹出对话框
        sensitize_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //找到视图,作为popupWindow显示的视图
                View view_phone = View.inflate(context, R.layout.activity_sens_pop_phone, null);
                //得到弹出框的控件
                setCheck(view_phone);
                //设置弹出框
                setPopupWindow(view_phone, 550);
                //设置显示 弹出框
                setShowPopup(view_phone);
                //打开弹出框点击监听
                setPopOnClick();
            }
        });
    }
    //弹出框里面的控件点击监听
    private void setPopOnClick() {
        //点击下一步弹出对话框
        sensitize_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到弹出框的值
                String sens_phone = sens_edit_phone.getText().toString();
                //设置取消弹出框
                setPopDis();
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
                setPopupWindow(view_pwd, 600);
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
        //得到下一步的控件
        sensitize_s = (Button)view.findViewById(R.id.sensitize_s);
        sens_edit_phone = (EditText) view.findViewById(R.id.sens_edit_phone);
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
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
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
        popupWindow.setFocusable(true);
    }
}
