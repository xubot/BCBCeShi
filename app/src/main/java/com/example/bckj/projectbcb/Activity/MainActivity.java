package com.example.bckj.projectbcb.Activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DaCheSuccessBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.DengLuBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiCodeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiTaskIdBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DiDiZhuCeBean;
import com.example.bckj.projectbcb.Bean.DiDiBean.DingDaiLieBiaoBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.GetPicCodeBeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.H5BeanData;
import com.example.bckj.projectbcb.Bean.DiDiBean.HuoQuYanZhengMaBeanData;
import com.example.bckj.projectbcb.Bean.LogBean;
import com.example.bckj.projectbcb.Bean.MessageEvent;
import com.example.bckj.projectbcb.Bean.SensitizelistBean;
import com.example.bckj.projectbcb.Presenter.PresenterLayer;
import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.CountDownTimerUtils;
import com.example.bckj.projectbcb.Utils.DecideWifi;
import com.example.bckj.projectbcb.Utils.DecideWifiAlertdialog;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiFourParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiOneParameter;
import com.example.bckj.projectbcb.Utils.DiDiUtils.DiDiTweParameter;
import com.example.bckj.projectbcb.Utils.SharedUtils;
import com.example.bckj.projectbcb.Utils.update.UpdateVersionController;
import com.example.bckj.projectbcb.ViewLayer.MainView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.lang.Thread.sleep;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class MainActivity extends BaseActivity implements MainView{
    private String url_ch="http://118.190.91.24:8080/freewifi/app/index.html?id=ch";
    private String url_en="http://118.190.91.24:8080/freewifi/app/index.html?id=en";
    private PresenterLayer presenterLayer = new PresenterLayer();
    private UpdateVersionController controller;
    private AlertDialog alertDialog;
    private AlertDialog dialog;
    private SharedUtils instance = SharedUtils.getInstance();
    private WebView myWebView;
    private int REQUEST_CODE=0;
    private boolean flag;
    private long exitTime=0;
    private double[] doubles;
    private boolean flags=true;
    private EditText[] mArray;
    private DrawerLayout mDrawerLayout;
    private Button home_img;
    private ImageView meunlogin,pic_code;
    private String APP_CACAHE_DIRNAME="/data/data/package_name/database/webviewCache.db";
    private TextView meunlog,china,englsh,taxi_meun_sens, taxi_meun_jihuo,codephone, resend;
    private EditText pice_one, pice_twe, pice_three, pice_frou;
    private EditText et_one, et_two, et_three,et_four;
    private LinearLayout yuyan_ll,ssss_ll,meunlog_ll;
    private int i=0;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //经度
                    double longitude = aMapLocation.getLongitude();
                    //纬度
                    double latitude = aMapLocation.getLatitude();
                    doubles = gaoDeToBaidu(longitude,latitude);
                    try {
                        //当页面加载完成后，调用js方法
                        //mWebview.loadUrl("javascript:方法名(参数)");
                        if(doubles==null){
                            return;
                        }else {
                            JSONObject json = new JSONObject();
                            json.put("lng", doubles[0]);
                            json.put("lat", doubles[1]);
                            Log.d("zzz", doubles[0]+""+doubles[1]);
                            myWebView.loadUrl("javascript:showMessage("+json.toString()+")");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /**
                     * 后期不用
                     * */
                    if(i<1){
                        setWebViewH5();
                        i++;
                        Log.d("zzz", "dfas" + i);
                    }
                }else {
                   //错误日志
                    int errorCode = aMapLocation.getErrorCode();
                    Log.d("zzz","main:"+ errorCode);
                }
            }
        }
    };


    //初始化布局
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        //得到刚进入就要用的控件方法
        initFindView();

        //判断连接的是否是局域网
        DecideWifiMethod(new DecideWifi(),"getVerificationCode","phoneNum","13051672112");

        //初始化EventBus
        EventBus.getDefault().register(this);

        //得到存储的标示
        String usre = (String) instance.getData(this, "usre", "");
        String pwd = (String) instance.getData(this, "pwd", "");
        Log.d("zzz", "main 登录成功后得到的值：" + usre + "==" + pwd);
        //判断本地是否有用户名和密码
        if(!usre.isEmpty()&usre.length()>0&&!pwd.isEmpty()&&pwd.length()>0){
            //有就直接登录
            presenterLayer.setMainLog(usre,pwd);
        }

        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= 23) {
            //判断用户是否开启权限
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    ||ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_WIFI_STATE},
                        REQUEST_CODE);
            } else {
                Toast.makeText(this, "请检查定位是否开启", Toast.LENGTH_SHORT).show();
                location();
            }
        }
        //开启定位
        location();
        setWebViewH5();

        //创建出更新版本的对象
        if (null == controller) {
            controller = UpdateVersionController.getInstance(this);
        }
        //版本更新的方法
        controller.normalCheckUpdateInfo();
    }

    //判断连接的是否是局域网
    private void DecideWifiMethod(final DecideWifi decideWifi, String modle, String parameter, String vaule) {
        Call oneCall = decideWifi.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("zzz", "连接正确，可进行下面的操作");
                return;
            }
        });
    }


    //得到刚进入就要用的控件方法
    public void initFindView() {
        //得到语言切换的控件组
        yuyan_ll = (LinearLayout) findViewById(R.id.yuyan_ll);
        //显示控件
        yuyan_ll.setVisibility(ViewGroup.VISIBLE);
        //得到中文控件
        china = (TextView) findViewById(R.id.ch);
        //得到英文控件
        englsh = (TextView) findViewById(R.id.en);
        //得到登录按钮
        meunlog = (TextView) findViewById(R.id.meunlog);
        //得到服务控件
        taxi_meun_sens = (TextView) findViewById(R.id.taxi_meun_sens);
        //得到激活控件
        taxi_meun_jihuo = (TextView) findViewById(R.id.taxi_meun_jihuo);
    }

    //得到登录成功时的唯一标示供后期判断（逻辑出现问题）
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setEventBus(MessageEvent eventBus){
        //得到登录状态
        flag = eventBus.getFlag();
        Log.d("zzz", "main里面返回的eventbus值："+flag);
        if(flag){
            //请求得到用户名的方法
            mainDataLogName();
            //得到登录成功的code值
            int mainLogCode = (int) instance.getData(this, "mainLogCode", 0);
            String token = (String) instance.getData(this, "token", "");
            Log.d("zzz", "main 得到本地存的taken值：=" +mainLogCode);
            if(mainLogCode==1){
                Log.d("zzz", "main 得到本地存的taken值：-" +token);
                presenterLayer.setSensitizelist(token);
                //登录滴滴
                String taxiaccount = (String) instance.getData(MainActivity.this, "taxiaccount", "");
                String taxipass = (String) instance.getData(MainActivity.this, "taxipass", "");
                Log.d("zzz", "Main   如果登录退出将自动登录滴滴的账号信息：" + taxiaccount + "==" + taxipass);
                if((taxiaccount.isEmpty()&&taxiaccount.length()==0)&&(taxipass.isEmpty()&&taxipass.length()==0)){
                    return;
                }else {
                    if(i<1){
                        logInTaskIDRequest(new DiDiTweParameter(), "login",taxiaccount,taxipass);
                        i++;
                    }else {
                        return;
                    }
                }
            }else {
                return;
            }
            //登陆后点击调到个人信息
            meunlog_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("zzz", "main    因为登录了所以这里走了");
                    startActivity(new Intent(MainActivity.this,PersonDataActivity.class));
                    finish();
                }
            });
        }else {
            //给登录控件赋值
            meunlog.setText(R.string.menu_log);
            Log.d("main", "main   因为没有登录所以这里走了");
            //点击跳到登录页面
            meunlog_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LogActivity.class));
                    finish();
                }
            });
        }
    }

    //得到控件
    @Override
    protected void init() {
        //侧滑按钮控件
        home_img = (Button) findViewById(R.id.home_img);
        //得到侧滑页
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //侧滑页里面的登录控件
        meunlog_ll = (LinearLayout)findViewById(R.id.ll);
        //激活服务的控件
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
                 Log.d("zzzz","main的当前语言：-"+ CH);

                 String token = (String) instance.getData(MainActivity.this, "token", "");
                 Log.d("zzz", "main 得到本地存的taken值：--" +token);
                 //得到登录成功的code值
                 int mainLogCode = (int) instance.getData(MainActivity.this, "mainLogCode", 0);
                 Log.d("zzz", "main 得到本地存的mainLogCode值：---" +mainLogCode);

                 china.setTextColor(getResources().getColor(R.color.textcolor));
                 englsh.setTextColor(Color.WHITE);
                 china.setBackgroundResource(R.drawable.p_1);
                 englsh.setBackgroundResource(R.drawable.p1);
                 if(flag||mainLogCode==1){
                     //请求得到用户名的方法
                     mainDataLogName();
                     presenterLayer.setSensitizelist(token);
                     updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
                     instance.saveData(MainActivity.this,"yuyan",CH);
                 }else if(flag||mainLogCode==0){
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
                Log.d("zzzz","main的当前语言：+"+ EN);

                String token = (String) instance.getData(MainActivity.this, "token", "");
                Log.d("zzz", "main 得到本地存的taken值：++" +token);
                //得到登录成功的code值
                int mainLogCode = (int) instance.getData(MainActivity.this, "mainLogCode", 0);
                Log.d("zzz", "main 得到本地存的mainLogCode值：---" +mainLogCode);
                china.setTextColor(Color.WHITE);

                englsh.setTextColor(getResources().getColor(R.color.textcolor));
                china.setBackgroundResource(R.drawable.p);
                englsh.setBackgroundResource(R.drawable.p1_1);
                if(flag||mainLogCode==1){
                    //请求得到用户名的方法
                    mainDataLogName();
                    presenterLayer.setSensitizelist(token);
                    updateLange(Locale.ENGLISH,url_en);
                    instance.saveData(MainActivity.this,"yuyan",EN);
                }else if(flag||mainLogCode==0){
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
                finish();
            }
        });
        //打车服务的点击监听
        ssss_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到服务激活的状态值
                String active = (String) instance.getData(MainActivity.this, "active", "");
                Log.d("zzz", "main  主页面从存储对点击服务监听状态码：" + active+"--");
                if(active.equals("1")){
                    Toast.makeText(MainActivity.this, R.string.mainjihuo, Toast.LENGTH_SHORT).show();
                    taxi_meun_jihuo.setText(R.string.yijihuo);
                }else {
                    taxi_meun_jihuo.setText(R.string.jihuo);
                    startActivity(new Intent(MainActivity.this,SensitizeActivity.class));
                    finish();
                }
            }
        });
    }
    //请求数据得到P层对象
    @Override
    protected void load() {
        presenterLayer.setMainView(this);
    }


    //加载html页面
    public void setWebViewH5() {
        myWebView = (WebView) findViewById(R.id.myWebView);
        String  yuyan = (String) instance.getData(MainActivity.this, "yuyan", "默认");
        Log.d("zzz", "main  得到当前的语言状态："+yuyan);
        Log.d("zzz", "main 开始调起webView");
        WebSettings webSettings = myWebView.getSettings();
        // 允许webview执行javaScript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置UserAgent
        String userAgent = webSettings.getUserAgentString();
        myWebView.getSettings().setUserAgentString(userAgent);

        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 DOM storage API 功能
        myWebView.getSettings().setDomStorageEnabled(true);
        //
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setAllowContentAccess(true);
        //启用数据库
        myWebView.getSettings().setDatabaseEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.setHorizontalScrollBarEnabled(false);
        myWebView.setHorizontalScrollbarOverlay(true);

        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //设置渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDefaultTextEncodingName("utf-8");

        //进入时判断
        if(yuyan.equals("默认")){
            Log.d("zzz","main: -"+yuyan);
            china.setTextColor(getResources().getColor(R.color.textcolor));
            englsh.setTextColor(Color.WHITE);
            china.setBackgroundResource(R.drawable.p_1);
            englsh.setBackgroundResource(R.drawable.p1);
            updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
        }else if(yuyan.equals("中文")){
            Log.d("zzz","main: +"+yuyan);
            china.setTextColor(getResources().getColor(R.color.textcolor));
            englsh.setTextColor(Color.WHITE);
            china.setBackgroundResource(R.drawable.p_1);
            englsh.setBackgroundResource(R.drawable.p1);
            updateLange(Locale.SIMPLIFIED_CHINESE,url_ch);
        }else if(yuyan.equals(" E N")){
            Log.d("zzz","main: ="+yuyan);
            china.setTextColor(Color.WHITE);
            englsh.setTextColor(getResources().getColor(R.color.textcolor));
            china.setBackgroundResource(R.drawable.p);
            englsh.setBackgroundResource(R.drawable.p1_1);
            updateLange(Locale.ENGLISH,url_en);
        }
        myWebView.addJavascriptInterface(new JS(MainActivity.this),"android");
        myWebView.setWebViewClient(new myWebViewClient());
        //配置权限
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            // 设置网页加载的进度条
            public void onProgressChanged(WebView view, int newProgress) {MainActivity.this.getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
                super.onProgressChanged(view, newProgress);
            }

            // 设置应用程序的标题title
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        if (isNetworkAvailable(getApplicationContext())) {
            //有网络连接，设置默认缓存模式
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //无网络连接，设置本地缓存模式
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }
    //通过h5跳转原生
    class JS{
        private Context context;
        private Handler mHandler = new Handler();
        public JS(Context context) {
            this.context = context;
        }
        //android4.2之后，如果不加上该注解，js无法调用android方法（安全）
        @JavascriptInterface
        public void showToast(final String json) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
                }
            });
        }

        //得到传来的地址调到 原生界面
        @JavascriptInterface
        public void get(String path){
            System.out.println("打印"+path);
            Intent intent = new Intent();
            intent.putExtra("url",path);
            Log.d("url", path);
            intent.setClass(MainActivity.this, ShowActivity.class);
            startActivity(intent);
        }
        //叫起打车调到原生界面
        @JavascriptInterface
        public void forTaxi(String json) {
            int mainLogCode = (int) instance.getData(MainActivity.this, "mainLogCode", 0);
            Log.d("zzz", "main  主页面从存储对象中得到的登录状态码：" + mainLogCode);
            if(mainLogCode==1){
                //得到服务激活的状态值
                String active = (String) instance.getData(MainActivity.this, "active", "");
                String taxicode = (String) instance.getData(MainActivity.this, "taxicode", "");
                Log.d("zzz", "main  主页面从存储对象中得到的服务激活的状态码：" + active+"--"+taxicode);
                if(active.equals("1")){
                    Gson gson = new Gson();
                    H5BeanData h5BeanData = gson.fromJson(json, H5BeanData.class);
                    String endAddre = h5BeanData.getEndAddre();
                    String endLat = h5BeanData.getEndLat();
                    String endLng = h5BeanData.getEndLng();
                    String startAddre = h5BeanData.getStartAddre();
                    String startLat = h5BeanData.getStartLat();
                    String startLng = h5BeanData.getStartLng();
                    Log.d("zzz","main  +:"+startAddre+""+startLat+""+startLng);
                    //得到起点位置
                    String start = stringToUnicode(startAddre);
                    //得到终点位置
                    String end = stringToUnicode(endAddre);
                    //调起滴滴打车功能
                    callTaxiTaskIDRequest(new DiDiFourParameter(),start,end,startLat,startLng,endLat,endLng);
                    //调起延迟跳转
                    //enterHome();
                }else {
                    Toast.makeText(context, "你还没有激活", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,SensitizeActivity.class));
                    finish();
                }
            }else {
                Log.d("zzz", "mian 你还没有登录请先登录:" + mainLogCode);
                startActivity(new Intent(MainActivity.this,LogActivity.class));
                finish();
            }
        }

        @JavascriptInterface
        //js那边的方法名
        public void hide(){
            //中英文控件
            ObjectAnimator
                    .ofFloat(yuyan_ll, "translationX", 0.0F, 360.0F)
                    .setDuration(500)
                    .start();
            //侧滑按钮控件
            ObjectAnimator
                    .ofFloat(home_img, "translationX", 0F,-360.0F)
                    .setDuration(500)
                    .start();
            System.out.println("打印:"+"走了雷锋精神浪费");
            flags=false;

        }
        @JavascriptInterface
        //js那边的方法名
        public void show(){
            //中英文控件
            ObjectAnimator
                    .ofFloat(yuyan_ll, "translationX", 360.0F,0.0F)
                    .setDuration(500)
                    .start();
            //侧滑按钮控件
            ObjectAnimator
                    .ofFloat(home_img, "translationX", -360.0F,0F)
                    .setDuration(500)
                    .start();
            System.out.println("打印:"+"ssss走了雷锋精神浪费");
            flags=true;
        }
        @JavascriptInterface
        //调用取消订单的方法
        public void cancelTaxi(){
            Log.d("zzz", "你点击的取消订单的方法");
            DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
            String taxiOid = (String) instance.getData(MainActivity.this, "taxiOid", "");
            Log.d("zzz", "取消订单得到taxioid:" + taxiOid);
            if(!taxiOid.equals("")){
                cancelOrderBeforeOrderTakingTaskIDRequest(diDiOneParameter,"cancelOrderBeforeOrderTaking","orderId",taxiOid);
                Log.d("zzz", "取消订单得到taxioid:gsdfdfsf");
            }
        }
    }

    //设置WebView类
    class myWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        //给h5传值
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                //当页面加载完成后，调用js方法
                //mWebview.loadUrl("javascript:方法名(参数)");
                //Log.d("zzz", doubles[0]+""+doubles[1]);
                if(doubles==null){
                    Toast.makeText(MainActivity.this, "请检查定位是否开启", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    JSONObject json = new JSONObject();
                    json.put("lng", doubles[0]);
                    json.put("lat", doubles[1]);
                    //Log.d("zzz", doubles[0]+""+doubles[1]);
                    myWebView.loadUrl("javascript:showMessage("+json.toString()+")");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    //请求得到用户名的方法
    private void mainDataLogName() {
        //得到登录的token值
        String token= (String) instance.getData(MainActivity.this, "token","");
        Log.d("zzz", "main  从本地得到token值：" + token);
        //得打个人数据
        presenterLayer.setData(token);
    }
    //得到用户名
    @Override
    public void getDataName(DataNameBean dataNameBean) {
        String msg = dataNameBean.getMsg();
        Log.d("zzz", "main  得到获得用户名的状态："+msg);
        DataNameBean.DataBean data = dataNameBean.getData();
        //得到用户名
        final String username = data.getUsername();
        Log.d("zzz", "main  得到获得用户名"+username);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                meunlog.setText(username);
            }
        });
    }
    //得到登录的数据
    @Override
    public void log(LogBean logBean) {
        int code = logBean.getCode();
        String msg = logBean.getMsg();
        String msg_en = logBean.getMsg_en();
        Log.d("zzz", "main 主页面的登录状态："+code+"--"+msg+"=="+msg_en);
        //判断关闭app时是否是登录状态
        if(code ==1){
            String token = logBean.getData().getToken();
            Log.d("zzz", "main 主页面得到的token值："+token);
            //存入登陆后的token值
            instance.saveData(MainActivity.this,"token",token);
            //请求得到用户名的方法
            mainDataLogName();
            //查看服务激活状态
            presenterLayer.setSensitizelist(token);

            //点击进入详情信息
            meunlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,PersonDataActivity.class));
                    finish();
                }
            });

            Log.d("zzz", "main 主页面的code值：" + code);
            //将登录成功的code值存入本地存储对象中
            instance.saveData(MainActivity.this,"mainLogCode",code);

            //发送EventBus标示
            EventBus.getDefault().post(new MessageEvent(true));
        }else if(code>90){
            Toast.makeText(this, "登录失效，重新登录", Toast.LENGTH_SHORT).show();
            Log.d("zzz", "main 主页面登录得到的信息：" +code+"--"+msg+"=="+msg_en);
        }else {
            Toast.makeText(this, msg+"\n"+msg_en+"\n"+"请重新登录", Toast.LENGTH_SHORT).show();
            Log.d("zzz", "main 主页面登录得到的信息：" +code+"--"+msg+"=="+msg_en);
        }
    }
    //得到服务激活后的列表
    @Override
    public void getsensitizelistBean(SensitizelistBean sensitizelistBean) {
        int code = sensitizelistBean.getCode();
        String msg = sensitizelistBean.getMsg();
        String msg_en = sensitizelistBean.getMsg_en();
        SensitizelistBean.DataBean data = sensitizelistBean.getData();
        SensitizelistBean.DataBean.ListBean list = data.getList();
        SensitizelistBean.DataBean.ListBean.TAXIBean taxi = list.getTAXI();
        //激活成功与否的状态码
        String active = taxi.getActive();
        //激活成功的名字
        String name = taxi.getName();
        //得到滴滴的信息列表
        SensitizelistBean.DataBean.ListBean.TAXIBean.InfoBean info = taxi.getInfo();
        //得到滴滴的状态
        String taxicode = info.getTaxi();
        //得到滴滴密码
        String taxiPass = info.getTaxiPass();
        //得到滴滴账号
        String taxiAccount = info.getTaxiAccount();
        Log.d("zzz","main 服务激活的列表信息："+code+"=="+active+"--"+name+"--"+taxicode+"++"+taxiPass+"--"+taxiAccount);
        //将激活成功的状态信息存到本地
        instance.saveData(MainActivity.this,"active",active);
        instance.saveData(MainActivity.this,"taxicode",taxicode);
        instance.saveData(MainActivity.this,"taxipass",taxiPass);
        instance.saveData(MainActivity.this,"taxiaccount",taxiAccount);
        if(active.equals("1")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    taxi_meun_jihuo.setText(R.string.yijihuo);
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    taxi_meun_jihuo.setText(R.string.jihuo);
                }
            });
        }
    }



    //图形验证码的对话框
    private void setPicCodeOnClick(Object pic) {
        Log.d("zzz", "从子线程中得到的图形码：" + pic);

        byte[] bytes = hexStrToByteArray(String.valueOf(pic));
        //打开获得图形验证码的对话框
        alertDialog = setAlertDialog(MainActivity.this, R.layout.activity_sens_pop_pic_code);
        //将byte转成bitmap对象
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.d("zzz", bitmap+"");
        //给图片验证码处赋值
        pic_code.setImageBitmap(bitmap);

        picCodeinitListener();

        /**点击图片换一张**/
        pic_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了重新换一张图片", Toast.LENGTH_SHORT).show();
                //得到一个参数的对象
                DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                //得到手机号的值
                String taxiaccount = (String) instance.getData(MainActivity.this, "taxiaccount", "");
                //调起滴滴获得验证码的方法
                verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",taxiaccount);
                alertDialog.cancel();
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完图形验证码自动跳转）
    private void picCodeinitListener() {
        mArray = new EditText[]{pice_one, pice_twe, pice_three,pice_frou};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (temp.length() == 1 && j >= 0 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }

                    if (temp.length() == 0) {
                        if (j >= 1) {
                            mArray[j - 1].setFocusable(true);
                            mArray[j - 1].setFocusableInTouchMode(true);
                            mArray[j - 1].requestFocus();
                        }
                    }
                    if (!TextUtils.isEmpty(pice_one.getText().toString().trim()) && !TextUtils.isEmpty(pice_twe.getText().toString().trim()) && !TextUtils.isEmpty(pice_three.getText().toString().trim()) && !TextUtils.isEmpty(pice_frou.getText().toString().trim())) {
                        String code = pice_one.getText().toString().trim() + pice_twe.getText().toString().trim() + pice_three.getText().toString().trim() + pice_frou.getText().toString().trim();
                        Toast.makeText(MainActivity.this,code, Toast.LENGTH_SHORT).show();
                        //得到一个参数的对象
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        //调起发送验证码的方法
                        setPhotoCodeTaskIDRequest(diDiOneParameter,"setPhotoCode","photoCode",code);
                    }
                }
            });
        }
    }

    //短信验证码对话框
    private void setPicToCodeOnClick() {
        //得到一个参数的对象
        final DiDiOneParameter diDiOneParameter = new DiDiOneParameter();

        //打开获得短信验证码的对话框
        alertDialog = setAlertDialog(MainActivity.this, R.layout.activity_sens_pop_code);
        //得到手机号的值
        final String taxiaccount = (String) instance.getData(MainActivity.this, "taxiaccount", "");
        //将手机号赋给验证码处
        codephone.setText("验证码发送至   "+taxiaccount);

        //设置EditText的文本改变的监听
        picToCodeinitListener();

        //开启倒计时
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 70000, 1000);
        mCountDownTimerUtils.start();

        /**点击重新发送验证码**/
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了重新发送", Toast.LENGTH_SHORT).show();
                //开启倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(resend, 70000, 1000);
                mCountDownTimerUtils.start();
                //调起滴滴获得验证码的方法
                verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",taxiaccount);
            }
        });
    }
    //接口，包括三个方法：（EditText的监听类  输入完验证码自动跳转）
    private void picToCodeinitListener() {
        mArray = new EditText[]{et_one, et_two, et_three,et_four};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp = s;
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (temp.length() == 1 && j >= 0 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }

                    if (temp.length() == 0) {
                        if (j >= 1) {
                            mArray[j - 1].setFocusable(true);
                            mArray[j - 1].setFocusableInTouchMode(true);
                            mArray[j - 1].requestFocus();
                        }
                    }
                    if (!TextUtils.isEmpty(et_one.getText().toString().trim()) && !TextUtils.isEmpty(et_two.getText().toString().trim()) && !TextUtils.isEmpty(et_three.getText().toString().trim()) && !TextUtils.isEmpty(et_four.getText().toString().trim())) {
                        String code = et_one.getText().toString().trim() + et_two.getText().toString().trim() + et_three.getText().toString().trim() + et_four.getText().toString().trim();
                        Toast.makeText(MainActivity.this,code, Toast.LENGTH_SHORT).show();
                        //得到一个参数的对象
                        DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                        //调起发送验证码的方法
                        submitePicTocodeTaskIDRequest(diDiOneParameter,"submitEcode","smsCode",code);
                    }
                }
            });
        }
    }


    //调起得到打车 滴滴的TaskId的请求方法
    private void callTaxiTaskIDRequest(final DiDiFourParameter diDiFourParameter,String startAddress,String endAddress,String slat,String slng,String elat,String elng) {
        Call oneCall = diDiFourParameter.okUitls(startAddress, endAddress,slat,slng,elat,elng);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //回到叫起打车的页面
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        myWebView.loadUrl("javascript:backHome()");
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                callTaxiDataRequest(diDiFourParameter,taskId);
            }
        });
    }
    //调起打车滴滴的数据请求的方法
    private void callTaxiDataRequest(final DiDiFourParameter diDiFourParameter, final String taskId) {
        Call tweCall = diDiFourParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                //回到叫起打车的页面
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        myWebView.loadUrl("javascript:backHome()");
                    }
                });

                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){

                    //回到叫起打车的页面
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            myWebView.loadUrl("javascript:backHome()");
                        }
                    });

                    threadToast(MainActivity.this,"滴滴没有开启");
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        callTaxiDataRequest(diDiFourParameter, taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }

                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    if (returnJSONStr.equals("fail")) {
                        threadToast(MainActivity.this,"异常");
                    } else {
                        StringBuilder sb = new StringBuilder(returnJSONStr);
                        Log.d("zzz", "main   打车要解析的字符串：" + sb.toString());
                        DaCheSuccessBeanData daCheSuccessBeanData = gson.fromJson(sb.toString(), DaCheSuccessBeanData.class);
                        String errorCode = daCheSuccessBeanData.getErrorCode();
                        String errorMessage = daCheSuccessBeanData.getErrorMessage();
                        if(errorCode.equals("0")){
                            DaCheSuccessBeanData.ResultBean result = daCheSuccessBeanData.getResult();
                            if(result.equals("null")){
                                return;
                            }else {
                                final int errno = result.getErrno();
                                final String errmsg = result.getErrmsg();
                                final String oid = result.getOid();
                                //将打车成功的oid存到本地
                                instance.saveData(MainActivity.this, "taxiOid", oid);
                                Log.d("zzz", "main   解析处理的oid : " + oid);
                                Log.d("zzz", "main  解析出来的errno : " + errno);
                                Log.d("zzz", "main  解析出来的errmsg : " + errmsg);

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (errno == 0) {
                                            //得到一个参数的对象
                                            DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                                            //查看司机信息
                                            getTaxiOrderTaskIDRequest(diDiOneParameter, "getTaxiOrder", "orderId", oid);
                                            Log.d("zzz", "main   成功的信息 : " + oid);
                                        } else if (errno == 101) {
                                            //回到叫起打车的页面
                                            myWebView.loadUrl("javascript:backHome()");

                                            String taxiaccount = (String) instance.getData(MainActivity.this, "taxiaccount", "");
                                            String taxipass = (String) instance.getData(MainActivity.this, "taxipass", "");
                                            Log.d("zzz", "Main   如果登录退出将自动登录滴滴的账号信息：" + taxiaccount + "==" + taxipass);
                                            if(taxiaccount.isEmpty()&&taxiaccount.length()==0&&taxipass.isEmpty()&&taxipass.length()==0){
                                                return;
                                            }else {
                                                logInTaskIDRequest(new DiDiTweParameter(), "login", taxiaccount, taxipass);
                                            }
                                        } else if (errno == 1018) {
                                            //回到叫起打车的页面
                                            myWebView.loadUrl("javascript:backHome()");

                                            final String taxiOid = (String) instance.getData(MainActivity.this, "taxiOid", "");
                                            Log.d("zzz", "Main  --将要为订单状态传过去的oid是："+ taxiOid);

                                            int status1= (int) instance.getData(MainActivity.this,"status1",5);
                                            Log.d("zzz", "Main  得到打车时返回后的状态："+ status1);
                                            if(status1==5){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                //设置对话框内的文本
                                                builder.setMessage("正在为你通知附近车辆，请等待。。。。");
                                                //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
                                                builder.setPositiveButton("取消订单", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // 执行点击确定按钮的业务逻辑
                                                        if(!taxiOid.equals("")){
                                                            cancelOrderBeforeOrderTakingTaskIDRequest(new DiDiOneParameter(),"cancelOrderBeforeOrderTaking","orderId",taxiOid);
                                                            Log.d("zzz", "已成功调起了取消订单的接口");
                                                        }
                                                    }
                                                });
                                                //使用builder创建出对话框对象
                                                dialog = builder.create();
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.setCancelable(false);
                                                //显示对话框
                                                dialog.show();
                                            }else if(status1==4){
                                                Toast.makeText(MainActivity.this, "您有一笔未完成的叫车订单,将为您恢复。请等待", Toast.LENGTH_SHORT).show();
                                                //打车成功跳到等待应答页面
                                                Intent intent = new Intent(MainActivity.this, TaxiActivity.class);
                                                intent.putExtra("oid",taxiOid+"");
                                                startActivity(intent);
                                            }
                                        } else {
                                            //回到叫起打车的页面
                                            myWebView.loadUrl("javascript:backHome()");

                                            Toast.makeText(MainActivity.this, "打车的错误信息：\n"+errmsg, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }else {
                            //回到叫起打车的页面
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    myWebView.loadUrl("javascript:backHome()");
                                }
                            });
                            threadToast(MainActivity.this,errorMessage);
                        }
                    }
                }
            }
        });
    }


    //调起取消订单 滴滴的TaskId的请求方法
    private void cancelOrderBeforeOrderTakingTaskIDRequest(final DiDiOneParameter diDiOneParameter, String modle, String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                cancelOrderBeforeOrderTakingDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起取消订单 滴滴的数据请求的方法
    private void cancelOrderBeforeOrderTakingDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "TaxiActivity  请求过程中错误的信息：--" + e.toString());
                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","TaxiActivity   得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","TaxiActivity   得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    threadToast(MainActivity.this,"滴滴没有开启");
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        cancelOrderBeforeOrderTakingDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "TaxiActivity   当前的状态值：" + status);
                        return;
                    }

                    Log.d("zzz", "TaxiActivity   当前的状态值：" + status);
                    Log.d("zzz", "TaxiActivity   终于到了done:" + returnJSONStr);
                    if(returnJSONStr.equals("success")){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                myWebView.loadUrl("javascript:backHome()");
                            }
                        });
                        threadToast(MainActivity.this,"您的订单已经取消成功");
                        if(dialog==null){
                           return;
                        }else {
                            dialog.cancel();
                        }
                    }else {
                        threadToast(MainActivity.this,"异常:"+returnJSONStr);
                        String taxiOid = (String) instance.getData(MainActivity.this, "taxiOid", "");
                        //调起请求数据的方法
                        cancelOrderBeforeOrderTakingTaskIDRequest(diDiOneParameter,"cancelOrderBeforeOrderTaking","orderId", taxiOid);
                    }

                }
            }
        });
    }


    //调起得到登录滴滴的TaskId的请求方法
    private void logInTaskIDRequest(final DiDiTweParameter diDiTweParameter,String modle,String value1,String value2) {
        Call oneCall = diDiTweParameter.okUitls(modle, value1, value2);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                logInDataRequest(diDiTweParameter,taskId);
            }
        });
    }
    //调起登录滴滴的数据请求的方法
    private void logInDataRequest(final DiDiTweParameter diDiTweParameter, final String taskId) {
        Call tweCall = diDiTweParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        logInDataRequest(diDiTweParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }

                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);

                    DengLuBeanData dengLuBeanData = gson.fromJson(returnJSONStr, DengLuBeanData.class);
                    int errorCode = dengLuBeanData.getErrorCode();
                    String errorMessage = dengLuBeanData.getErrorMessage();
                    if(errorCode!=0){
                        threadToast(MainActivity.this,errorMessage);
                    }else {
                        DengLuBeanData.ResultBean result = dengLuBeanData.getResult();
                        final String errno = result.getErrno();
                        final String error = result.getError();
                        Log.d("zzzz", "sensitize  解析出需要的值："+errno +"=="+ error);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(errno.equals("0")){
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                }else if(errno.equals("-425")){
                                    threadToast(MainActivity.this,error);
                                    //调起获取验证码的接口
                                    //得到一个参数的对象
                                    DiDiOneParameter diDiOneParameter = new DiDiOneParameter();
                                    //得到手机号的值
                                    String taxiaccount = (String) instance.getData(MainActivity.this, "taxiaccount", "");
                                    //调起滴滴获得验证码的方法
                                    verificationCodeTaskIDRequest(diDiOneParameter,"getVerificationCode","phoneNum",taxiaccount);
                                }else {
                                    Toast.makeText(MainActivity.this, "登录失败：\n" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    //调起得到订单详情 滴滴的TaskId的请求方法
    private void getTaxiOrderTaskIDRequest(final DiDiOneParameter diDiOneParameter, String modle, String parameter, String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                getTaxiOrderDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起订单详情 滴滴的数据请求的方法
    private void getTaxiOrderDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "TaxiActivity  请求过程中错误的信息：--" + e.toString());
                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","TaxiActivity   得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","TaxiActivity   得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        getTaxiOrderDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "TaxiActivity   当前的状态值：" + status);
                        return;
                    }

                    Log.d("zzz", "TaxiActivity   当前的状态值：" + status);
                    Log.d("zzz", "TaxiActivity   终于到了done:" + returnJSONStr);
                    if(returnJSONStr.equals("Listener")){
                        threadToast(MainActivity.this,"附近没有车辆");
                        String taxiOid = (String) instance.getData(MainActivity.this, "taxiOid", "");
                        //调起请求数据的方法
                        getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId", taxiOid);
                    }else if(returnJSONStr.equals("非出租车订单！")){
                        threadToast(MainActivity.this,"距离太近无法生成订单");
                    }else {
                        //得到司机的数据
                        DingDaiLieBiaoBeanData dingDaiLieBiaoBeanData = gson.fromJson(returnJSONStr, DingDaiLieBiaoBeanData.class);
                        int status1 = dingDaiLieBiaoBeanData.getStatus();
                        final String oid1 = dingDaiLieBiaoBeanData.getOid();
                        //将状态保存
                        instance.saveData(MainActivity.this,"status1",status1);
                        if(status1==5){
                            Log.d("zzzstatus1", "TaxiActivity   得到订单的状态码："+status1+"=="+oid1);
                            //调起请求数据的方法
                            getTaxiOrderTaskIDRequest(diDiOneParameter,"getTaxiOrder","orderId", oid1);
                        }else if(status1==4){

                            //回到叫起打车的页面
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    myWebView.loadUrl("javascript:backHome()");
                                }
                            });

                            Log.d("zzz", "司机已经接单了");
                            Log.d("zzzstatus1", "TaxiActivity   得到订单的状态码----："+status1+"=="+oid1);
                            DingDaiLieBiaoBeanData.ImInfoBean imInfo = dingDaiLieBiaoBeanData.getImInfo();
                            String skey = imInfo.getSkey();
                            instance.saveData(MainActivity.this,"driverSkey",skey);
                            String uname = imInfo.getUname();
                            String uid = imInfo.getUid();
                            DingDaiLieBiaoBeanData.TaxiDriverBean taxiDriver = dingDaiLieBiaoBeanData.getTaxiDriver();
                            if(taxiDriver!=null) {
                                String card = taxiDriver.getCard();
                                String name = taxiDriver.getName();
                                String phone = taxiDriver.getPhone();
                                int level = taxiDriver.getLevel();
                                int orderCount = taxiDriver.getOrderCount();
                                String did = taxiDriver.getDid();
                                Log.d("zzz", "TaxiActivity   得到的司机信息时" + card + "--" + uname + "==" + phone + "===" + level + "---" + orderCount + "--" + did);

                                //将后面需要的值存起来
                                instance.saveData(MainActivity.this, "driverPhone", phone);
                                instance.saveData(MainActivity.this, "driverDid", uid);
                                instance.saveData(MainActivity.this, "driverName", uname);
                                instance.saveData(MainActivity.this, "driverCard",card);
                                instance.saveData(MainActivity.this, "driverLevel",level);
                                instance.saveData(MainActivity.this, "driverOrderCount",orderCount);
                                instance.saveData(MainActivity.this, "driveroid",oid1);

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                            Toast.makeText(MainActivity.this, "已有司机成功接单了", Toast.LENGTH_SHORT).show();
                                            //打车成功跳到等待应答页面
                                            Intent intent = new Intent(MainActivity.this, TaxiActivity.class);
                                            startActivity(intent);
                                    }
                                });
                                if(dialog==null){
                                    return;
                                }else {
                                    dialog.cancel();
                                }
                            }
                        }
                    }
                }
            }
        });
    }


    //调起得到获取验证码滴滴的 TaskId的请求方法
    private void verificationCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                verificationCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起获取验证码滴滴的求数据请的方法
    private void verificationCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        verificationCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    GetPicCodeBeanData getPicCodeBeanData = gson.fromJson(returnJSONStr, GetPicCodeBeanData.class);
                    int errorCode = getPicCodeBeanData.errorCode;
                    String errorMessage = getPicCodeBeanData.errorMessage;
                    if(errorCode!=0){
                        threadToast(MainActivity.this,errorMessage);
                    }else {
                        Log.d("zzz", "得到验证码之类的值：" + errorMessage + "==" + errorCode);
                        GetPicCodeBeanData.ResultBean result = getPicCodeBeanData.result;
                        List<Object> values = result.values;
                        if(values==null){
                            threadToast(MainActivity.this,"获得短信验证码");
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPicToCodeOnClick();
                                }
                            });
                        }else {
                            Object msg = values.get(0);
                            final Object pic= values.get(1);
                            Log.d("zzz", "得到验证码之类的值："+msg+"=="+pic);
                            DiDiCodeBean diDiCodeBean = gson.fromJson(String.valueOf(msg), DiDiCodeBean.class);
                            String errno = diDiCodeBean.getErrno();
                            String error = diDiCodeBean.getError();
                            Log.d("zzz", "得到验证码之类的值集合0里面的值："+errno+ "==" + error);
                            if(errno.equals("1003")){
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPicCodeOnClick(pic);
                                    }
                                });
                            }else {
                                threadToast(MainActivity.this,error);
                            }
                        }
                    }
                }
            }
        });
    }


    //调起得到发送图形验证码 滴滴的TaskId的请求方法
    private void setPhotoCodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                setPhotoCodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起发送图形验证码滴滴的数据请求的方法
    private void setPhotoCodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "滴滴没有开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        setPhotoCodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                }
                Log.d("zzz", "当前的状态值：" + status);
                Log.d("zzz", "终于到了done:" + returnJSONStr);
                GetPicCodeBeanData getPicCodeBeanData = gson.fromJson(returnJSONStr, GetPicCodeBeanData.class);
                int errorCode = getPicCodeBeanData.errorCode;
                String errorMessage = getPicCodeBeanData.errorMessage;
                if(errorCode!=0){
                    threadToast(MainActivity.this,errorMessage);
                }else {
                    Log.d("zzz", "得到验证码之类的值：" + errorMessage + "==" + errorCode);
                    GetPicCodeBeanData.ResultBean result = getPicCodeBeanData.result;
                    List<Object> values = result.values;
                    if(values==null){
                        threadToast(MainActivity.this,"验证成功");
                        HuoQuYanZhengMaBeanData huoQuYanZhengMaBeanData = gson.fromJson(returnJSONStr, HuoQuYanZhengMaBeanData.class);
                        HuoQuYanZhengMaBeanData.ResultBean result1 = huoQuYanZhengMaBeanData.getResult();
                        String errno = result1.getErrno();
                        String error = result1.getError();
                        Log.d("zzz", "得到验证码之类的值："+errno+ "==" +error);
                        if(errno.equals("0")){
                            alertDialog.cancel();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPicToCodeOnClick();
                                }
                            });
                        }else {
                            threadToast(MainActivity.this,"图形验证码的信息："+error);
                        }
                    }else {
                        Object msg = values.get(0);
                        final Object pic= values.get(1);
                        Log.d("zzz", "得到验证码之类的值："+msg+ "==" +pic);
                        DiDiCodeBean diDiCodeBean = gson.fromJson(String.valueOf(msg), DiDiCodeBean.class);
                        String errno = diDiCodeBean.getErrno();
                        String error = diDiCodeBean.getError();
                        Log.d("zzz", "得到验证码之类的值集合0里面的值："+errno+ "==" + error);
                        if(errno.equals("2002")){
                            alertDialog.cancel();
                            threadToast(MainActivity.this,error);
                            Log.d("zzz", "得到验证错误后的图形：" +pic);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setPicCodeOnClick(pic);
                                }
                            });
                        }else {
                            threadToast(MainActivity.this,error);
                        }
                    }
                }
            }
        });
    }


    //调起得到发送验证码滴滴的TaskId的请求方法
    private void submitePicTocodeTaskIDRequest(final DiDiOneParameter diDiOneParameter,String modle,String parameter,String vaule) {
        Call oneCall = diDiOneParameter.okUitls(modle,parameter,vaule);
        //开始请求
        oneCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new DecideWifiAlertdialog().netAlertdialog(MainActivity.this);
                    }
                });
                Log.d("zzz", "请求过程中错误的信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //得到gson对象
                Gson gson = new Gson();
                //得到bean类
                DiDiTaskIdBean taskIdBean = gson.fromJson(data, DiDiTaskIdBean.class);
                //得到bean类里面的值
                String taskId = taskIdBean.getTaskId();
                //调起请求数据的方法
                submitePicTocodeDataRequest(diDiOneParameter,taskId);
            }
        });
    }
    //调起发送验证码滴滴的数据请求的方法
    private void submitePicTocodeDataRequest(final DiDiOneParameter diDiOneParameter, final String taskId) {
        Call tweCall = diDiOneParameter.okUitls1(taskId);
        tweCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz", "请求过程中错误的信息：--" + e.toString());
                threadToast(MainActivity.this,"连接超时，请重试");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //得到请求返回的值
                String data = response.body().string();
                Log.d("zzz","得到值是："+data);
                //解析数据
                Gson gson = new Gson();
                //得到解析的bean
                DiDiZhuCeBean dataBean = gson.fromJson(data, DiDiZhuCeBean.class);
                //得到值
                String status = dataBean.getStatus();
                String returnJSONStr = dataBean.getReturnJSONStr();

                Log.d("zzz","得到值是："+status+"------"+returnJSONStr);
                if(status.equals("fail")){
                    threadToast(MainActivity.this,"滴滴没有开启");
                }else {
                    while (!status.equals("done")) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调起请求数据的方法
                        submitePicTocodeDataRequest(diDiOneParameter,taskId);
                        Log.d("zzz", "当前的状态值：" + status);
                        return;
                    }
                    Log.d("zzz", "当前的状态值：" + status);
                    Log.d("zzz", "终于到了done:" + returnJSONStr);
                    if(returnJSONStr.equals("none")){
                        return;
                    }else {
                        //得到最终要解析的值
                        DiDiCodeBean diDiCodeBean = gson.fromJson(returnJSONStr, DiDiCodeBean.class);
                        final String errno = diDiCodeBean.getErrno();
                        final String error = diDiCodeBean.getError();
                        Log.d("zzzz", "sensitize  解析出需要的值："+errno +"=="+ error);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(errno.equals("0")){
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    alertDialog.cancel();
                                }else {
                                    Toast.makeText(MainActivity.this,error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    //弹出对话框的属性
    public AlertDialog setAlertDialog(Context context,int view){
        //得到对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //得到对话框的对象
        AlertDialog alertDialog = builder.create();
        //得到要加载的布局
        View alertView = View.inflate(context, view, null);
        //得到对话框里的控件
        setCheck(alertView);
        //设置点击不消失
        alertDialog.setCanceledOnTouchOutside(false);
        //将布局设置到对话框
        alertDialog.setView(alertView);
        //显示对话框
        alertDialog.show();
        return alertDialog;
    }
    //得到弹出框的控件
    private void setCheck(View view){
        codephone = (TextView) view.findViewById(R.id.codephone);
        //得到调到设置密码的控件
        resend = (TextView) view.findViewById(R.id.resend);
        //将要跳到验证码的控件
        pic_code = (ImageView) view.findViewById(R.id.pic_code);
        //得到输入图形验证码的控件
        pice_one = (EditText) view.findViewById(R.id.pice_one);
        pice_twe = (EditText) view.findViewById(R.id.pice_twe);
        pice_three = (EditText) view.findViewById(R.id.pice_three);
        pice_frou = (EditText) view.findViewById(R.id.pice_frou);
        //PopupWindow的布局控件
        et_one = (EditText) view.findViewById(R.id.e_one);
        et_two = (EditText) view.findViewById(R.id.e_twe);
        et_three = (EditText) view.findViewById(R.id.e_three);
        et_four = (EditText) view.findViewById(R.id.e_frou);
    }


    //切换语言
    private void updateLange(Locale locale,String url){
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //Toast.makeText(this, "Locale in "+locale+" !", Toast.LENGTH_LONG).show();
        refresh(url);  // 刷新當前頁面
    }
    //切换语言刷新界面的方法
    public void refresh(String url) {
        myWebView.loadUrl(url);
        meunlog.setText(R.string.menu_log);
        taxi_meun_sens.setText(R.string.taxi_sens);
        taxi_meun_jihuo.setText(R.string.jihuo);
    }

    //定位的方法
    public void location(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //定时刷新
        mLocationOption.setInterval(5000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //转码（将高德坐标转换成百度坐标）
    private double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    //将16进制改成btye数组
    private byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    //将字符串转成uniCode
    public String stringToUnicode(String vaule) {
        String str = "";
        for (int i = 0; i < vaule.length(); i++) {
            int ch = (int) vaule.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += "\\" + Integer.toHexString(ch);
        }
        return str;
    }

    //延迟跳转的时间
    private void enterHome() {
        Timer time = new Timer();
        TimerTask tk = new TimerTask() {
            Intent intent = new Intent(MainActivity.this, TaxiActivity.class);
            @Override
            public void run() {
                startActivity(intent);
                //finish();
            }
        };
        time.schedule(tk, 3000);
    }

    //得到权限回调的方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //开启定位
                location();
                //Toast.makeText(this, "获取！", Toast.LENGTH_SHORT).show();
            } else {
                //用户不同意，向用户展示该权限作用
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setMessage("本应用需要定位你目前的位置，不赋予定位权限将无法正常工作！")
                            .setPositiveButton("前往授权", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    // 根据包名打开对应的设置界面
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                }
                            }).create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                    return;
                }
                Toast.makeText(this, "访问被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //解决在子线程中吐司的方法
    public void threadToast(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // 此按键监听的是返回键，能够返回到上一个网页（通过网页的hostlistery）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(flags){
                exit();
            }else {
                myWebView.loadUrl("javascript:hideTip()");
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //点两次退出的方法
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), R.string.mainquit_again,
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
