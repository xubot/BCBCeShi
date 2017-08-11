package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.LogoutBean;
import com.example.bckj.projectbcb.Bean.MessageEvent;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.JavaScriptinterface;
import com.example.bckj.projectbcb.Utils.SharedUtils;
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
    private SharedUtils instance;
    private WebView myWebView;

    //初始化布局
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        //初始化EventBus
        EventBus.getDefault().register(this);
        //加载html页面
        setWebViewH5();
    }

    //得到控件
    @Override
    protected void init() {
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
                //得到登录的token值
                instance = SharedUtils.getInstance();
                String token = (String) instance.getData(MainActivity.this, "token", "");
                Log.d("zzz", "logout=" + token);
                //调起退出
                presenterLayer.setLogout(token);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setEventBus(MessageEvent eventBus){
        //得到登录状态
        flag = eventBus.getFlag();
        Log.d("sxd", flag +"");
        if(flag){
            meunlog.setText("默认用户名");
            //String token = (String) instance.getData(MainActivity.this, "token", "");
            //Log.d("xxx", "eventbus=" + token);
            //得打个人数据
            //presenterLayer.setData(token);
            //登陆后点击调到个人信息
            meunlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,PersonDataActivity.class));
                }
            });
        }else {
            meunlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LogActivity.class));
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
    //得到用户名
    @Override
    public void getDataName(DataNameBean dataNameBean) {
        String msg = dataNameBean.getMsg();
        Log.d("xxx", msg);
        DataNameBean.DataBean data = dataNameBean.getData();
        //得到用户名
        final String username = data.getUsername();
        Log.d("xxx", username);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                meunlog.setText(username);
            }
        });
    }
    //退出登录信息
    @Override
    public void getLogout(LogoutBean logoutBean) {
        int code = logoutBean.getCode();
        String msg = logoutBean.getMsg();
        String msg_en = logoutBean.getMsg_en();
        if(code==1){
            Toast.makeText(this, msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent(false));
            finish();
        }else {
            Toast.makeText(this,  msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }

    //加载html页面
    private void setWebViewH5() {
        myWebView = (WebView) findViewById(R.id.myWebView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        myWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");
        myWebView.loadUrl("http://118.190.91.24:8080/freewifi/index.html");
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new myWebViewClient());
    }

    //设置WebView类
    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
    }

    // 此按键监听的是返回键，能够返回到上一个网页（通过网页的hostlistery）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
