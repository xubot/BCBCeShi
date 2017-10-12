package com.example.bckj.projectbcb.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;

import pl.droidsonroids.gif.GifImageView;

public class ShowActivity extends AppCompatActivity {

    private WebView mWebView;
    private SharedUtils instance;
    private GifImageView show_back;
    String APP_CACAHE_DIRNAME="/data/data/package_name/database/webviewCache.db";
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        show_back = (GifImageView) findViewById(R.id.show_back);
        instance = SharedUtils.getInstance();
        Intent intent = getIntent();
        String path = intent.getStringExtra("url");
        Log.d("zzz", "path:"+path);
        String flag = (String) instance.getData(this, "yuyan", "默认");
        Log.d("aasd", flag);
        if(flag.equals("中文")){
            init1(path);
        }else if(flag.equals(" E N")){
            init(path);
        }else if(flag.equals("默认")){
            init1(path);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                show_back.setVisibility(View.GONE); //view是要隐藏的控件
            }
        }, 3000);  //3000毫秒后执行
    }
    //英文网页
    private void init(String path){
        String url=path;
        mWebView =(WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.requestFocus();
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (!url.startsWith("http") && !url.startsWith("https")){
                        return false;
                    }
                } catch (Exception e) {
                    //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("zzz", "打算加载js插件");
                String insertJavaScript = "javascript:setTimeout(function(){{var s=document.createElement('script');s.type='text/javascript';s.charset='UTF-8';s.src=((location && location.href && location.href.indexOf('https') == 0)?'https://ssl.microsofttranslator.com':'http://www.microsofttranslator.com')+'/ajax/v3/WidgetV3.ashx?siteData=ueOIGRSKkd965FeEGM5JtQ**&ctf=False&ui=true&settings=undefined&from=zh-CHS';var p=document.getElementsByTagName('head')[0]||document.documentElement;p.insertBefore(s,p.firstChild); }},0)";
                //添加翻译的按钮
                view.loadUrl("javascript:$('.scope-page-detail').append('<button   style=\"opacity: 0;position:absolute;left:50%;margin:12px 0 12px -65px;height:27px;padding:0 0 30px 0;overflow:hidden;border:3px solid #00a3ff;border-radius:4px;background:#fff;color:#00a3ff\" id=\"MicrosoftTranslatorWidget\"></button>')");
                view.loadUrl("javascript:$('.place-page-detail').append('<button   style=\"opacity: 0;position:absolute;left:50%;margin:12px 0 12px -65px;height:27px;padding:0 0 30px 0;overflow:hidden;border:3px solid #00a3ff;border-radius:4px;background:#fff;color:#00a3ff\" id=\"MicrosoftTranslatorWidget\"></button>')");
                view.loadUrl("javascript:$('#cater-page-detail').append('<button   style=\"opacity: 0;position:absolute;left:50%;margin:12px 0 12px -65px;height:27px;padding:0 0 30px 0;overflow:hidden;border:3px solid #00a3ff;border-radius:4px;background:#fff;color:#00a3ff\" id=\"MicrosoftTranslatorWidget\"></button>')");
                //添加翻译的脚本
                view.loadUrl(insertJavaScript);
                Log.d("zzz", "添加成功");
                //删除某些元素
                view.loadUrl("javascript:$('.captain-bar').css('display','none')");
                view.loadUrl("javascript:$('#nav_maplink').css('display','none!important')");
                view.loadUrl("javascript:$('.place-widget-banknearby').css('display','none')");
                view.loadUrl("javascript:$('.scope-widget-book').css('display','none')");
                view.loadUrl("javascript:$('.bottom-banner-float').css('display','none')");
                view.loadUrl("javascript:$('.common-footer-widget').css('display','none !important')");
                view.loadUrl("javascript:$('.common-widget-footer').css('display','none')");
                view.loadUrl("javascript:$('#scope_streetview').css('display','none')");
                view.loadUrl("javascript:$('.needsclick').css('display','none !important')");
                view.loadUrl("javascript:$('.captain-img').attr('data-href','#')");
                view.loadUrl("javascript:function tim(){ if(document.getElementById(\"MicrosoftTranslatorWidget\")){\n" +
                        "$('#TranslateSpan').click();$('#WidgetFloaterPanels').hide();$('#MicrosoftTranslatorWidget').hide()} };setInterval('tim()',200)");
                Log.d("zzzz", "结束");
                /*view.loadUrl("javascript:$('#MicrosoftTranslatorWidget').click(function(){$('#WidgetFloaterPanels').css('top','95%').css('left','0')})");
                view.loadUrl("javascript:$('#WidgetFloaterPanels').css('top','95%').css('left','0')");*/

            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }
    //中文网页
    private void init1(String path){
        String url=path;
        mWebView =(WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.requestFocus();
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);
        //启用数据库
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(true);

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        //设置渲染的优先级
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //设置可以访问文件
        mWebView.getSettings().setAllowFileAccess(true);

        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        mWebView.getSettings().setGeolocationDatabasePath(dir);

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (!url.startsWith("http") && !url.startsWith("https")){
                        return false;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //删除某些元素
                view.loadUrl("javascript:$('.captain-bar').css('display','none')");
                view.loadUrl("javascript:$('#nav_maplink').css('display','none!important')");
                view.loadUrl("javascript:$('.place-widget-banknearby').css('display','none')");
                view.loadUrl("javascript:$('.scope-widget-book').css('display','none')");
                view.loadUrl("javascript:$('.bottom-banner-float').css('display','none')");
                view.loadUrl("javascript:$('.common-footer-widget').css('display','none !important')");
                view.loadUrl("javascript:$('.common-widget-footer').css('display','none')");
                view.loadUrl("javascript:$('#scope_streetview').css('display','none')");
                view.loadUrl("javascript:$('.captain-img').attr('data-href','#')");
                view.loadUrl("javascript:$('.needsclick').css('display','none !important')");
                Log.d("zzzz", 12344566 + "");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        mWebView.loadUrl(url);
    }
}
