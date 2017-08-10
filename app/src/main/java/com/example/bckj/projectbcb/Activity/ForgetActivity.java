package com.example.bckj.projectbcb.Activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bckj.projectbcb.Bean.BackBean;
import com.example.bckj.projectbcb.Bean.CodeBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
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
        setToolBar("",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
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
                String emal = forget_edit_usre.getText().toString();
                presenterLayer.setForget_Code(emal);
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
                presenterLayer.setForget_Back(emal,code,pwd,repwd);
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
        String msg = codeBean.getMsg();
        String msg_en = codeBean.getMsg_en();
        Log.d("zzz", "qqq:" + msg+"\n" + msg_en);
    }

    @Override
    public void back(BackBean backBean) {
        String msg = backBean.getMsg();
        String msg_en = backBean.getMsg_en();
        Log.d("zzz", "www:" + msg+"\n" + msg_en);
    }
}
