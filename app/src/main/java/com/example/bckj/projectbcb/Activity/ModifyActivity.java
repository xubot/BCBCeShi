package com.example.bckj.projectbcb.Activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.Modifypwd;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.ModifyView;

public class ModifyActivity extends BaseActivity implements ModifyView{
    private EditText oldpwd;
    private EditText newpwd;
    private EditText newrepwd;
    private PresenterLayer presenterLayer;
    private TextView complete;
    private String token;

    @Override
    public void initView() {
        setContentView(R.layout.activity_modify);
        setToolBar("修改密码",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
        SharedUtils instance = SharedUtils.getInstance();
        token = (String) instance.getData(this, "token", "");
    }

    @Override
    protected void init() {
        oldpwd = (EditText) findViewById(R.id.oldpwd);
        newpwd = (EditText) findViewById(R.id.newpwd);
        newrepwd = (EditText) findViewById(R.id.newrepwd);
        complete = (TextView) findViewById(R.id.complete);
    }

    @Override
    public void cheked() {
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zzz", "Modify=" + token);
                String oldPwd = oldpwd.getText().toString();
                String newPwd = newpwd.getText().toString();
                String newRePwd = newrepwd.getText().toString();
                presenterLayer.setModifyPwd(oldPwd,newPwd,newRePwd,token);
            }
        });
    }

    @Override
    public void onClike(ImageView img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setModifypwd(this);
    }

    @Override
    public void getModify(Modifypwd modifypwd) {
        int code = modifypwd.getCode();
        String msg = modifypwd.getMsg();
        String msg_en = modifypwd.getMsg_en();
        Log.d("zzz",code+"\n"+ msg + "\n" + msg_en);
        if(code==1){
            Toast.makeText(this, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }
}
