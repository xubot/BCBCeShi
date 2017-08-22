package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private LinearLayout meunlog_ll;
    private PresenterLayer presenterLayer;
    private SharedUtils instance;
    private WebView myWebView;
    private Button home_img;
    private boolean flag;
    private TextView meunlog,china,englsh;
    private String url_ch="http://118.190.91.24:8080/freewifi/app/index.html?id=ch";
    private String url_en="http://118.190.91.24:8080/freewifi/app/index.html?id=en";
    private long exitTime=0;
    private LinearLayout yuyan_ll;
    private LinearLayout ssss_ll;

    //初始化布局
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        yuyan_ll = (LinearLayout) findViewById(R.id.yuyan_ll);
        china = (TextView) findViewById(R.id.ch);
        englsh = (TextView) findViewById(R.id.en);
        meunlog = (TextView) findViewById(R.id.meunlog);
        //显示控件
        yuyan_ll.setVisibility(ViewGroup.VISIBLE);

        instance = SharedUtils.getInstance();
        //加载html页面
        setWebViewH5();
        //初始化EventBus
        EventBus.getDefault().register(this);
    }
    //得到登录成功时的唯一标示共供期判断（逻辑出现问题）
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setEventBus(MessageEvent eventBus){
        //得到登录状态
        flag = eventBus.getFlag();
        Log.d("sxd", flag +"");
        if(flag){
            //请求得到用户名的方法
            mainDataLogName();
            //登陆后点击调到个人信息
            meunlog_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("sxd", "走这了");
                    startActivity(new Intent(MainActivity.this,PersonDataActivity.class));
                }
            });
            /*//打车服务的点击监听
            ssss_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,SensitizeActivity.class));
                }
            });*/
        }else {
            meunlog.setText(R.string.menu_log);
            Log.d("sxd1", "走这了");
            meunlog_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LogActivity.class));
                }
            });
            /*//打车服务的点击监听
            ssss_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LogActivity.class));
                }
            });*/
        }
    }

    //得到控件
    @Override
    protected void init() {
        //侧滑按钮控件
        home_img = (Button) findViewById(R.id.home_img);
        home_img.setVisibility(ViewGroup.VISIBLE);
        //得到侧滑页
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //侧滑页里面的登录控件
        meunlog_ll = (LinearLayout)findViewById(R.id.ll);
        ssss_ll = (LinearLayout) findViewById(R.id.ssss_ll);
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
        //中文点击监听
        china.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String CH = china.getText().toString();
                 china.setTextColor(getResources().getColor(R.color.textcolor));
                 englsh.setTextColor(Color.BLACK);
                 china.setBackgroundResource(R.drawable.p_1);
                 englsh.setBackgroundResource(R.drawable.p1);
                 if(flag){
                     //请求得到用户名的方法
                     mainDataLogName();
                     updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
                     instance.saveData(MainActivity.this,"yuyan",CH);

                 }else{
                     updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
                     instance.saveData(MainActivity.this,"yuyan",CH);
                 }
             }
         });
        //英文点击监听
        englsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EN = englsh.getText().toString();
                china.setTextColor(Color.BLACK);
                englsh.setTextColor(getResources().getColor(R.color.textcolor));
                china.setBackgroundResource(R.drawable.p);
                englsh.setBackgroundResource(R.drawable.p1_1);
                if(flag){
                    //请求得到用户名的方法
                    mainDataLogName();
                    updateLange(Locale.ENGLISH,url_en);
                    instance.saveData(MainActivity.this,"yuyan",EN);
                }else{
                    updateLange(Locale.ENGLISH,url_en);
                    instance.saveData(MainActivity.this,"yuyan",EN);
                }
            }
        });

        //登录监听
        meunlog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LogActivity.class));
            }
        });
        //打车服务的点击监听
        ssss_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SensitizeActivity.class));
            }
        });

    }
    //请求数据
    @Override
    protected void load() {
        presenterLayer = new PresenterLayer();
        presenterLayer.setMainView(this);
    }

    //请求得到用户名的方法
    private void mainDataLogName() {
        //得到登录的token值
        String token= (String) instance.getData(MainActivity.this, "token", "");
        Log.d("xxx", "eventbus=" + token);
        //得打个人数据
        presenterLayer.setData(token);
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

    //加载html页面
    private void setWebViewH5() {
        String  yuyan = (String) instance.getData(MainActivity.this, "yuyan", "");
        Log.d("zzzx", yuyan);
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
        //进入时判断
        if(yuyan.equals("")){
            china.setTextColor(getResources().getColor(R.color.textcolor));
            englsh.setTextColor(Color.BLACK);
            china.setBackgroundResource(R.drawable.p_1);
            englsh.setBackgroundResource(R.drawable.p1);
            updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
        }else if(yuyan.equals("中文")){
            china.setTextColor(getResources().getColor(R.color.textcolor));
            englsh.setTextColor(Color.BLACK);
            china.setBackgroundResource(R.drawable.p_1);
            englsh.setBackgroundResource(R.drawable.p1);
            updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
        }else if(yuyan.equals("英文")){
            china.setTextColor(Color.BLACK);
            englsh.setTextColor(getResources().getColor(R.color.textcolor));
            china.setBackgroundResource(R.drawable.p);
            englsh.setBackgroundResource(R.drawable.p1_1);
            updateLange(Locale.ENGLISH,url_en);
        }
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
        //得到传来的地址调到 原生界面
        @JavascriptInterface
        public void get(String path){
            System.out.println("打印"+path);
            Intent intent = new Intent();
            intent.putExtra("url",path);
            intent.setClass(MainActivity.this, ShowActivity.class);
            startActivity(intent);
        }
        //叫起打车调到原生界面
        @JavascriptInterface
        public void forTaxi() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TaxiActivity.class);
            startActivity(intent);
        }
        @JavascriptInterface
        //js那边的方法名
        public void hide(){
            System.out.println("打印:"+"走了雷锋精神浪费");
            //隐藏控件
            yuyan_ll.setVisibility(ViewGroup.GONE);
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

    //切换语言
    private void updateLange(Locale locale,String url){
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, "Locale in "+locale+" !", Toast.LENGTH_LONG).show();
        refresh(url);  // 刷新當前頁面
    }

    //切换语言刷新界面的方法
    public void refresh(String url) {
        myWebView.loadUrl(url);
        meunlog.setText(R.string.menu_log);

    }

    // 此按键监听的是返回键，能够返回到上一个网页（通过网页的hostlistery）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //点两次退出的方法
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
