package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.LogBean;
import com.example.bckj.projectbcb.Bean.LoginBean;
import com.example.bckj.projectbcb.Bean.MessageEvent;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.LoginView;

import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity implements LoginView{

    private EditText login_edit_usre;
    private EditText login_edit_email;
    private EditText login_edit_pwd;
    private EditText login_edit_repwd;
    private EditText login_edit_phone;
    private Button login_login;
    private PresenterLayer presenterLayer;
    private SharedUtils instance;
    private String login_email;
    private String login_pwd;

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
                login_email = login_edit_email.getText().toString();
                login_pwd = login_edit_pwd.getText().toString();
                String login_repwd = login_edit_repwd.getText().toString();
                String login_phone = login_edit_phone.getText().toString();
                boolean login_usreName = TextUtils.isEmpty(login_usre);
                boolean login_emails = TextUtils.isEmpty(login_email);
                boolean login_password = TextUtils.isEmpty(login_pwd);
                boolean login_repassword = TextUtils.isEmpty(login_repwd);
                boolean login_phones = TextUtils.isEmpty(login_phone);
                if(login_phone!=null&&login_phone.length()!=0){
                    if(login_emails||login_usreName||login_password||login_repassword){
                        Toast.makeText(LoginActivity.this, R.string.panNull, Toast.LENGTH_SHORT).show();
                    }else {
                        //email的正则表达式
                        boolean email = isEmail(login_email);
                        Log.d("email", email+"");//(对为true)
                        //手机号的正则表达式
                        boolean mobile = isMobile(login_phone);
                        if(email){
                            if(login_pwd.length()>6&&login_repwd.length()>6){
                                if(!login_pwd.equals(login_repwd)){
                                    Toast.makeText(LoginActivity.this, R.string.panTwePwd, Toast.LENGTH_SHORT).show();
                                }else {
                                    if(mobile){
                                        //请求
                                        presenterLayer.setLoginOne(login_usre, login_email, login_pwd,login_repwd,login_phone);
                                    }else {
                                        Toast.makeText(LoginActivity.this,R.string.panPhone, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, R.string.panPwd, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,  R.string.panEmail, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if(login_emails||login_usreName||login_password||login_repassword){
                        Toast.makeText(LoginActivity.this, R.string.panNull, Toast.LENGTH_SHORT).show();
                    }else {
                        //email的正则表达式
                        boolean email = isEmail(login_email);
                        Log.d("email", email+"");//(对为true)
                        if(email){
                            if(login_pwd.length()>6&&login_repwd.length()>6){
                                if(!login_pwd.equals(login_repwd)){
                                    Toast.makeText(LoginActivity.this, R.string.panTwePwd, Toast.LENGTH_SHORT).show();
                                }else {
                                    //请求
                                    presenterLayer.setLoginTwe(login_usre, login_email, login_pwd,login_repwd);
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, R.string.panPwd, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,  R.string.panEmail, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    //判断邮箱的正则表达式
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }
    //判断手机号的正则标的是
    public static boolean isMobile(String mobile) {
        return Pattern.matches( "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", mobile);
    }

    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
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
            Toast.makeText(this, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            LoginBean.DataBean data = loginBean.getData();
            String token = data.getToken();
            //存入注册token值
            instance.saveData(LoginActivity.this,"logintoken",token);
            Log.d("zzz", "login 得到注册后的用户名密码"+login_email + "---" + login_pwd);
            presenterLayer.setPersonDataLog(login_email,login_pwd);

            Log.d("zzz", "login 注册状态：\n"+code + "\n" + msg + "\n" + msg_en);
            finish();
        }else {
            Toast.makeText(this,msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            Log.d("zzz", "login 注册状态：\n"+code + "\n" + msg + "\n" + msg_en);
        }
    }

    @Override
    public void log(LogBean logBean) {
        int code = logBean.getCode();
        String msg = logBean.getMsg();
        String msg_en = logBean.getMsg_en();
        if(code==1){
            //将登陆的token存入本地
            String token = logBean.getData().getToken();
            instance.saveData(LoginActivity.this,"token",token);

            Log.d("zzz", "login  得到登录后的用户名和密码："+login_email + "---" + login_pwd);
            //将用户名和密码存入本地
            instance.saveData(LoginActivity.this,"usre",login_email);
            instance.saveData(LoginActivity.this,"pwd",login_pwd);

            Log.d("zzz", "log  登录状态码："+code);
            //将登录成功的code值存入本地存储对象中
            instance.saveData(LoginActivity.this,"mainLogCode",code);

            Toast.makeText(this, "login:"+msg, Toast.LENGTH_SHORT).show();
            //发送EventBus标示
            EventBus.getDefault().post(new MessageEvent(true));
            startActivity(new Intent(LoginActivity.this,SensitizeActivity.class));
        }
    }
    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            startActivity(new Intent(LoginActivity.this,LogActivity.class));
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }*/
}
