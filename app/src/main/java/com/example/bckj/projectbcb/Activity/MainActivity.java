package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.LogoutBean;
import com.example.bckj.projectbcb.Bean.MessageEvent;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.ViewLayer.MainView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends BaseActivity implements MainView{

    private TextView home_taxi;
    private ImageView home_img;
    private DrawerLayout mDrawerLayout;
    private TextView meunlog;
    private boolean flag;
    private PresenterLayer presenterLayer;
    private LinearLayout modify_ll;
    private LinearLayout logout_ll;

    //初始化布局
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
    }
    //得到控件
    @Override
    protected void init() {
        //打车控件
        home_taxi = (TextView) findViewById(R.id.home_taxi);
        //侧滑按钮控件
        home_img = (ImageView) findViewById(R.id.home_img);
        //得到侧滑页
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //侧滑页里面的登录控件
        meunlog = (TextView)findViewById(R.id.meunlog);
        //修改密码控件
        modify_ll = (LinearLayout) findViewById(R.id.modify_ll);
        //退出控件
        logout_ll = (LinearLayout) findViewById(R.id.logout_ll);
    }
    //控件的点击事件
    @Override
    public void cheked(){
        //调起打车按钮监听
        home_taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaxiActivity.class));
            }
        });
        //调起侧滑（按钮按下，将抽屉打开）
        home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        //登录监听
        meunlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LogActivity.class));
            }
        });
        //修改密码
        modify_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ModifyActivity.class));
            }
        });
        //退出监听
        logout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLayer.setLogout();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setEventBus(MessageEvent eventBus){
        //得到登录状态
        flag = eventBus.getFlag();
        Log.d("sxd", flag +"");
        if(flag){
            //得打个人数据
            //presenterLayer.setData();
            //登陆后点击调到个人信息
            meunlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,PersonDataActivity.class));
                }
            });
        }
    }
    //请求数据
    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setMainView(this);
    }

    @Override
    public void getDataName(DataNameBean dataNameBean) {
        String msg = dataNameBean.getMsg();
        Log.d("zzz", msg);
        DataNameBean.DataBean data = dataNameBean.getData();
        //得到用户名
        final String username = data.getUsername();
        Log.d("zzz", username);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                meunlog.setText(username);
            }
        });
    }

    @Override
    public void getLogout(LogoutBean logoutBean) {
        String msg = logoutBean.getMsg();
        String msg_en = logoutBean.getMsg_en();
        Log.d("zzz","out:"+msg+"\n"+msg_en);
    }
}
