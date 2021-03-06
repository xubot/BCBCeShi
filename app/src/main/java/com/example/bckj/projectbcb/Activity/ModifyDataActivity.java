package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.UpdateInfoBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.MadifyDataView;

public class ModifyDataActivity extends BaseActivity implements MadifyDataView {

    private SharedUtils instance;
    private String token;
    private EditText m_name;
    private EditText m_phone;
    private Button modify_finish;
    private PresenterLayer presenterLayer;

    @Override
    public void initView() {
        setContentView(R.layout.activity_modify_data);
        setToolBar("",R.mipmap.back_02,R.color.one);
        //得到登陆后的token值
        instance = SharedUtils.getInstance();
        token = (String) instance.getData(this, "token", "");
    }

    @Override
    protected void init() {
        m_name = (EditText) findViewById(R.id.Mdetails_name);
        m_phone = (EditText) findViewById(R.id.Mdetails_phone);
        modify_finish = (Button) findViewById(R.id.modify_finish);
    }

    @Override
    public void cheked() {
        //修改完成的点监听
        modify_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = m_name.getText().toString();
                String phone = m_phone.getText().toString();
                Log.d("zzz", "ModifyData  请求修改个人信息用到的参数：" + name + "==" + phone + "--" + token);
                //修改请求
                presenterLayer.setUpdataInfo(name,phone,token);
            }
        });
    }

    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setModifyDataView(this);
    }
    //返回按钮监听
    @Override
    public void onClike(LinearLayout img1) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyDataActivity.this,PersonDataActivity.class));
                finish();
            }
        });
    }
    //修改信息
    @Override
    public void getUpdataInfoTwe(UpdateInfoBean updateInfoBean) {
        int code = updateInfoBean.getCode();
        String msg = updateInfoBean.getMsg();
        String msg_en = updateInfoBean.getMsg_en();
        Log.d("zzz", "ModifyData   修改信息情况：" + msg + "\n" + code + "\n" + msg_en);
        if(code==1){
            Toast.makeText(this, "修改信息成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ModifyDataActivity.this,PersonDataActivity.class));
            finish();
        }else {
            Toast.makeText(this,msg+"/n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            startActivity(new Intent(ModifyDataActivity.this,PersonDataActivity.class));
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
