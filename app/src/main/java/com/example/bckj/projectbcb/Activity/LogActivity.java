package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    public void initView() {
        setContentView(R.layout.activity_log);
        setToolBar("",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
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
                String usre = log_usre.getText().toString();
                //得到密码
                String pwd = log_pwd.getText().toString();
                //发起登录请求
                presenterLayer.setLog(usre,pwd);
            }
        });
        //注册监听
        log_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this,LoginActivity.class));
                finish();
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
        String token = logBean.getData().getToken();
        instance.saveData(LogActivity.this,"token",token);
        Log.d("zzz", code + "\n" + msg + "\n" + msg_en+"\n"+token);
        if(code==1){
            EventBus.getDefault().post(new MessageEvent(true));
            finish();
        }else {
            Log.d("zzz", code + "\n" + msg + "\n" + msg_en);
        }

    }
}
