package com.example.bckj.projectbcb.Activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.PersonDataBean;
import com.example.bckj.projectbcb.Bean.ReActiveUserBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.PersonDataView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PersonDataActivity extends BaseActivity implements PersonDataView{

    private PresenterLayer presenterLayer;
    private TextView modifypic;
    private TextView details_name;
    private TextView details_phone;
    private TextView details_emal;
    private TextView details_again;
    private String token;
    private ImageView personhead;

    @Override
    public void initView() {
        setContentView(R.layout.activity_person_data);
        setToolBar("",R.mipmap.back_02,R.color.one,R.menu.zhihu_toolbar_menu);
        //得到登陆后的token值
        SharedUtils instance = SharedUtils.getInstance();
        token = (String) instance.getData(this, "token", "");
    }

    @Override
    protected void init() {
        //得到邮箱控件
        details_emal = (TextView) findViewById(R.id.details_emal);
        //得到手机号控件
        details_phone = (TextView) findViewById(R.id.details_phone);
        //得到用户名控件
        details_name = (TextView) findViewById(R.id.details_name);
        //得到图片控件
        personhead = (ImageView) findViewById(R.id.personhead);
        //得到修改头像控件
        modifypic = (TextView) findViewById(R.id.modifypic);
        //得到重新获取的控件
        details_again = (TextView) findViewById(R.id.details_again);
    }

    @Override
    public void cheked() {
        //修改头像监听
        modifypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonDataActivity.this, "已点击修改头像", Toast.LENGTH_SHORT).show();
            }
        });
        details_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zzz", "details_again=" + token);
                //调起得到重新激活邮件
                presenterLayer.setreActiveUser(token);
            }
        });
    }

    @Override
    protected void load() {
        Log.d("zzz", "load=" + token);
        presenterLayer = new PresenterLayer();
        presenterLayer.setPersonDataView(this);
        //调起得到个人信息
        presenterLayer.setPersonData(token);
    }
    //得到个人信息
    @Override
    public void personData(PersonDataBean personDataBean) {
        String msg = personDataBean.getMsg();
        String msg_en = personDataBean.getMsg_en();
        DataNameBean.DataBean data = personDataBean.getData();
        final String email = data.getEmail();
        final String headpic = data.getHeadpic();
        final String mobile = data.getMobile();
        String status = data.getStatus();
        final String username = data.getUsername();
        Log.d("zzz", "person=" + msg + "\n" + username + "\n" + email+"\n"+headpic);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                details_name.setText(username);
                details_emal.setText(email);
                if(mobile==null){
                    details_phone.setText("未填写");
                }else {
                    details_phone.setText(mobile);
                }
                //显示图片的配置
                DisplayImageOptions options = getImagerBuild();
                //设置图片
                ImageLoader.getInstance().displayImage(headpic, personhead, options);
            }
        });
    }
    //得到重新激活邮件
    @Override
    public void reActiveData(ReActiveUserBean reActiveUserBean) {
        int code = reActiveUserBean.getCode();
        String msg = reActiveUserBean.getMsg();
        String msg_en = reActiveUserBean.getMsg_en();
        if(code==1){
            Toast.makeText(this, "重新激活的链接已发送:"+msg+"\n The reactivated link has been sent:"+msg_en, Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }

    //显示图片的配置
    private DisplayImageOptions getImagerBuild() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //比默认的速度快效率高
                .bitmapConfig(Bitmap.Config.RGB_565)
                //给图片设置圆角  int值 表示 圆角的半径大小 值越大 越圆
                .displayer(new RoundedBitmapDisplayer(60))
                .build();
    }
}
