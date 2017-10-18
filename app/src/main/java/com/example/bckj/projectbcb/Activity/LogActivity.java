package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.LogBean;
import com.example.bckj.projectbcb.Bean.MessageEvent;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.LogView;

import de.greenrobot.event.EventBus;

public class LogActivity extends BaseActivity implements LogView{

    private EditText log_usre;
    private EditText log_pwd;
    private Button log;
    private TextView log_login;
    private TextView forget;
    private PresenterLayer presenterLayer;
    private SharedUtils instance;
    private String usre;
    private String pwd;

    @Override
    public void initView() {
        setContentView(R.layout.activity_log);
        setToolBar("",R.mipmap.back_02,R.color.one);
        //得到存储对象
        instance = SharedUtils.getInstance();
    }

    @Override
    protected void init() {
        //得到用户名输入框
        log_usre = (EditText) findViewById(R.id.log_edit_usre);
        //得到密码输入框
        log_pwd = (EditText) findViewById(R.id.log_edit_pwd);
        //得到登录的控件
        log = (Button) findViewById(R.id.log);
        //得到注册的控件
        log_login = (TextView) findViewById(R.id.log_login);
        //得到忘记密码控件
        forget = (TextView) findViewById(R.id.forget);
    }
    @Override
    public void cheked() {
        //登录监听
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到账号
                usre = log_usre.getText().toString();
                //得到密码
                pwd = log_pwd.getText().toString();
                boolean username = TextUtils.isEmpty(usre);
                boolean password = TextUtils.isEmpty(pwd);
                if(username||password) {
                    Toast.makeText(LogActivity.this, R.string.panNull, Toast.LENGTH_SHORT).show();
                }else {
                    //判断密码不能小于6位
                    if(pwd.length()>=6){
                        //发起登录请求
                        presenterLayer.setLog(usre, pwd);
                    }else {
                        Toast.makeText(LogActivity.this,R.string.panPwd, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //注册监听
        log_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this,LoginActivity.class));
                //finish();
            }
        });
        //忘记密码监听
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this,ForgetActivity.class));
            }
        });
    }
    //返回按钮监听
    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setLogView(this);
    }

    @Override
    public void log(LogBean logBean) {
        int code = logBean.getCode();
        String msg = logBean.getMsg();
        String msg_en = logBean.getMsg_en();
        if(code==1){
            String token = logBean.getData().getToken();
            //存入登陆后的token值
            instance.saveData(LogActivity.this,"token",token);

            Log.d("zzz", "log  得到用户名和密码值："+usre + "===" + pwd);
            //将用户名和密码存入本地
            instance.saveData(LogActivity.this,"usre",usre);
            instance.saveData(LogActivity.this,"pwd",pwd);

            Log.d("zzz", "log  登录状态码："+code);
            //将登录成功的code值存入本地存储对象中
            instance.saveData(LogActivity.this,"mainLogCode",code);

            Toast.makeText(this, msg + "\n" + msg_en, Toast.LENGTH_SHORT).show();
            Log.d("zzz","登录状态：\n"+ code + "\n" + msg + "\n" + msg_en+"\n"+token);
            //发送EventBus标示
            EventBus.getDefault().post(new MessageEvent(true));
            startActivity(new Intent(LogActivity.this,MainActivity.class));
            finish();
        }else {
            Toast.makeText(this, msg + "\n" + msg_en, Toast.LENGTH_SHORT).show();
            Log.d("zzz", "登录状态：\n"+code + "\n" + msg + "\n" + msg_en);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            startActivity(new Intent(LogActivity.this,MainActivity.class));
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
