package com.example.bckj.projectbcb.Activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.BackBean;
import com.example.bckj.projectbcb.Bean.CodeBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.CountDownTimerUtils;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.ForgetView;

public class ForgetActivity extends BaseActivity implements ForgetView{

    private PresenterLayer presenterLayer;
    private EditText forget_edit_usre;
    private EditText forget_edit_code;
    private EditText forget_edit_pwd;
    private EditText forget_edit_repwd;
    private TextView forget_getcode;
    private Button forget_log;

    @Override
    public void initView() {
        setContentView(R.layout.activity_forget);
        setToolBar("",R.mipmap.back_02,R.color.one);
    }

    @Override
    protected void init() {
        forget_edit_usre = (EditText) findViewById(R.id.forget_edit_usre);
        forget_edit_code = (EditText) findViewById(R.id.forget_edit_code);
        forget_edit_pwd = (EditText) findViewById(R.id.forget_edit_pwd);
        forget_edit_repwd = (EditText) findViewById(R.id.forget_edit_repwd);
        forget_getcode = (TextView) findViewById(R.id.forget_getcode);
        forget_log = (Button) findViewById(R.id.forget_log);
    }

    @Override
    public void cheked() {
        //得到验证码监听
        forget_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证码倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(forget_getcode, 60000, 1000);
                mCountDownTimerUtils.start();
                String emal = forget_edit_usre.getText().toString();
                SharedUtils instance = SharedUtils.getInstance();
                String yuyan = (String)instance.getData(ForgetActivity.this, "yuyan","默认");
                Log.d("yuyan", yuyan);
                if(yuyan.equals("中文")){
                    presenterLayer.setForget_Code(emal,1);
                }else if(yuyan.equals(" E N")){
                    presenterLayer.setForget_Code(emal,2);
                }else {
                    presenterLayer.setForget_Code(emal,1);
                }
            }
        });
        //得到找回监听
        forget_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emal = forget_edit_usre.getText().toString();
                String code = forget_edit_code.getText().toString();
                String pwd = forget_edit_pwd.getText().toString();
                String repwd = forget_edit_repwd.getText().toString();
                //邮箱正则表达式
                boolean email = isEmail(emal);
                //判断处理
                boolean emails = TextUtils.isEmpty(emal);
                boolean codeData = TextUtils.isEmpty(code);
                boolean password = TextUtils.isEmpty(pwd);
                boolean repassword = TextUtils.isEmpty(repwd);
                if(emails||codeData||password||repassword){
                    Toast.makeText(ForgetActivity.this, R.string.panNull, Toast.LENGTH_SHORT).show();
                }else {
                    if(pwd.length()>=6&&repwd.length()>=6){
                        if(!pwd.equals(repwd)){
                            Toast.makeText(ForgetActivity.this,R.string.panTwePwd, Toast.LENGTH_SHORT).show();
                        } else {
                            //请求
                            presenterLayer.setForget_Back(emal,code,pwd,repwd);
                        }
                    }else {
                        Toast.makeText(ForgetActivity.this,R.string.panPwd, Toast.LENGTH_SHORT).show();
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
    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ForgetActivity.this,LogActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setForgetView(this);
    }

    @Override
    public void code(CodeBean codeBean) {
        int code = codeBean.getCode();
        String msg = codeBean.getMsg();
        String msg_en = codeBean.getMsg_en();
        Log.d("zzz", "forget   发送找回密码的情况："+code+"\n" + msg + "\n" + msg_en);
        Toast.makeText(this,msg+"\n"+ msg_en, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void back(BackBean backBean) {
        int code = backBean.getCode();
        String msg = backBean.getMsg();
        String msg_en = backBean.getMsg_en();
        Log.d("zzz", "forget   找回密码的情况："+ code+"\n"+ msg + "\n" + msg_en);
        if(code==1){
            Toast.makeText(this,msg+"\n" + msg_en, Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this,msg+"\n" + msg_en, Toast.LENGTH_SHORT).show();
        }
    }
}
