package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.LogoutBean;
import com.example.bckj.projectbcb.Bean.MessageEvent;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.ViewLayer.MainView;

import java.util.Locale;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends BaseActivity implements MainView{
    private DrawerLayout mDrawerLayout;
    private TextView meunlog;
    private boolean flag;
    private PresenterLayer presenterLayer;
    private LinearLayout modify_ll;
    private LinearLayout logout_ll;
    private SharedUtils instance;
    private WebView myWebView;
    private Button home_img;
    private TextView talk;
    private String yuyan;
    private TextView modify;
    private TextView edit;
    private String url_ch="http://118.190.91.24:8080/freewifi/index.html?id=ch";
    private String url_en="http://118.190.91.24:8080/freewifi/index.html?id=en";
    //初始化布局
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        instance = SharedUtils.getInstance();
        //加载html页面
        setWebViewH5(url_en);
        //初始化EventBus
        EventBus.getDefault().register(this);
        yuyan = (String) instance.getData(this, "yuyan", "");
        Log.d("kfs", yuyan);
    }
    public void first(){
        if(yuyan.equals("En")){
            talk.setText("En");
           /* meunlog.setText("Log In");
            modify.setText("Change password");
            edit.setText("Exit");*/
        }else if(yuyan.equals("中文")){
            talk.setText("中文");
           /* meunlog.setText("登录");
            modify.setText("修改密码");
            edit.setText("退出");*/
        }
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
                    Log.d("sxd", "走这了");
                    startActivity(new Intent(MainActivity.this,PersonDataActivity.class));
                }
            });
        }else {
            Log.d("sxd1", "走这了");
            meunlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LogActivity.class));
                }
            });
        }
    }

    //得到控件
    @Override
    protected void init() {
        //侧滑按钮控件
        home_img = (Button) findViewById(R.id.home_img);
        //切换语言的控件
        talk = (TextView) findViewById(R.id.talk);
        //得到侧滑页
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //侧滑页里面的登录控件
        meunlog = (TextView)findViewById(R.id.meunlog);
        //修改密码控件组
        modify_ll = (LinearLayout) findViewById(R.id.modify_ll);
        //修改密码控件
        modify = (TextView) findViewById(R.id.modify);
        //退出控件组
        logout_ll = (LinearLayout) findViewById(R.id.logout_ll);
        //退出控件
        edit = (TextView) findViewById(R.id.edit);
        //first();
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
        //切换语言的点击监听
        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = talk.getText().toString();
                if(flag.equals("中文")){
                    //setWebViewH5(url_ch);
                    Toast.makeText(MainActivity.this, "中文:"+flag, Toast.LENGTH_SHORT).show();
                    instance.saveData(MainActivity.this,"yuyan",flag);
                }else if(flag.equals("En")){
                    //setWebViewH5(url_en);
                    instance.saveData(MainActivity.this,"yuyan",flag);
                    Toast.makeText(MainActivity.this, "En:"+flag, Toast.LENGTH_SHORT).show();
                }
                String sta=getResources().getConfiguration().locale.getLanguage();
                shiftLanguage(sta);
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
                String token = (String) instance.getData(MainActivity.this, "token", "");
                Log.d("zzz", "logout=" + token);
                //调起退出
                presenterLayer.setLogout(token);
            }
        });
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
            //EventBus.getDefault().post(new MessageEvent(false));
            finish();
        }else {
            Toast.makeText(this,  msg+"\n"+msg_en, Toast.LENGTH_SHORT).show();
        }
    }

    //加载html页面
    private void setWebViewH5(String url) {
        myWebView = (WebView) findViewById(R.id.myWebView);
        WebSettings webSettings = myWebView.getSettings();
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDefaultTextEncodingName("utf-8");
        myWebView.loadUrl(url);
        myWebView.addJavascriptInterface(new JS(), "android");
        myWebView.setWebViewClient(new myWebViewClient());
        //配置权限
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
    }
    //通过h5跳转原生
    class JS{
        @JavascriptInterface
        public void get(String p){
            System.out.println("打印"+p);
            Intent intent = new Intent();
            intent.putExtra("num",p);
            intent.setClass(MainActivity.this, ShowActivity.class);
            startActivity(intent);
        }
        @JavascriptInterface
        public void forTaxi() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TaxiActivity.class);
            startActivity(intent);
        }
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
    //求换语言的方法
    public void shiftLanguage(String sta){
        if(sta.equals("zh")){
            Locale.setDefault(Locale.ENGLISH);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = Locale.ENGLISH;
            getBaseContext().getResources().updateConfiguration(config
                    , getBaseContext().getResources().getDisplayMetrics());
            refreshSelf();
        }else{
            Locale.setDefault(Locale.CHINESE);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = Locale.CHINESE;
            getBaseContext().getResources().updateConfiguration(config
                    , getBaseContext().getResources().getDisplayMetrics());
            refreshSelf();
        }
    }
    public void refreshSelf(){
        Intent intent=new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
