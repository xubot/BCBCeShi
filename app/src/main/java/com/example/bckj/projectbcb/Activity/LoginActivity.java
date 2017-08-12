package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.LoginBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.LoginView;

public class LoginActivity extends BaseActivity implements LoginView{

    private EditText login_edit_usre;
    private EditText login_edit_email;
    private EditText login_edit_pwd;
    private EditText login_edit_repwd;
    private EditText login_edit_phone;
    private Button login_login;
    private PresenterLayer presenterLayer;
    private SharedUtils instance;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        setToolBar("",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
        //得到存储对象
        instance = SharedUtils.getInstance();
    }

    @Override
    protected void init() {
        //得到用户名控件
        login_edit_usre = (EditText) findViewById(R.id.login_edit_usre);
        //得到 邮箱控件
        login_edit_email = (EditText) findViewById(R.id.login_edit_email);
        //得到密码控件
        login_edit_pwd = (EditText) findViewById(R.id.login_edit_pwd);
        //得到确认密码控件
        login_edit_repwd = (EditText) findViewById(R.id.login_edit_repwd);
        //得到手机号
        login_edit_phone = (EditText) findViewById(R.id.login_edit_phone);
        //得到注册控件
        login_login = (Button) findViewById(R.id.login_login);
    }
    @Override
    public void cheked() {
        //注册监听
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_usre = login_edit_usre.getText().toString();
                String login_email = login_edit_email.getText().toString();
                String login_pwd = login_edit_pwd.getText().toString();
                String login_repwd = login_edit_repwd.getText().toString();
                String login_phone = login_edit_phone.getText().toString();
                if(login_phone!=null){
                    presenterLayer.setLoginOne(login_usre,login_email,login_pwd,login_repwd,login_phone);
                } else {
                    presenterLayer.setLoginTwe(login_usre,login_email,login_pwd,login_repwd);
                }
            }
        });
    }

    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setLoginView(this);
    }

    @Override
    public void login(LoginBean loginBean) {
        int code = loginBean.getCode();
        String msg = loginBean.getMsg();
        String msg_en = loginBean.getMsg_en();

        if(code==1){
            Toast.makeText(this, "注册："+msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            LoginBean.DataBean data = loginBean.getData();
            String token = data.getToken();
            //存入注册token值
            instance.saveData(LoginActivity.this,"logintoken",token);
            startActivity(new Intent(LoginActivity.this,SensitizeActivity.class));
            finish();
        }else {
            Toast.makeText(this,msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            Log.d("zzz", code + "\n" + msg + "\n" + msg_en);
        }
    }
}
