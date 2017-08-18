package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bckj.projectbcb.R;
import com.example.bckj.projectbcb.Utils.SharedUtils;

public class ShowActivity extends AppCompatActivity {

    private WebView mWebView;
    private SharedUtils instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        instance = SharedUtils.getInstance();
        Intent intent = getIntent();
        String path = intent.getStringExtra("num");
        String flag = (String) instance.getData(this, "yuyan", "");
        Log.d("aasd", flag);
        if(flag.equals("中文")){
            init1(path);
        }else if(flag.equals("En")){
            init(path);
        }
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
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String insertJavaScript = "javascript:setTimeout(function(){{var s=document.createElement('script');s.type='text/javascript';s.charset='UTF-8';s.src=((location && location.href && location.href.indexOf('https') == 0)?'https://ssl.microsofttranslator.com':'http://www.microsofttranslator.com')+'/ajax/v3/WidgetV3.ashx?siteData=ueOIGRSKkd965FeEGM5JtQ**&ctf=False&ui=true&settings=undefined&from=zh-CHS';var p=document.getElementsByTagName('head')[0]||document.documentElement;p.insertBefore(s,p.firstChild); }},0)";
                //添加翻译的按钮
                view.loadUrl("javascript:$('.scope-address').attr('id','MicrosoftTranslatorWidget')");
                //添加翻译的脚本
                view.loadUrl(insertJavaScript);
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
                Log.d("zzzz", 12344566 + "");
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
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               /* String insertJavaScript = "javascript:setTimeout(function(){{var s=document.createElement('script');s.type='text/javascript';s.charset='UTF-8';s.src=((location && location.href && location.href.indexOf('https') == 0)?'https://ssl.microsofttranslator.com':'http://www.microsofttranslator.com')+'/ajax/v3/WidgetV3.ashx?siteData=ueOIGRSKkd965FeEGM5JtQ**&ctf=False&ui=true&settings=undefined&from=zh-CHS';var p=document.getElementsByTagName('head')[0]||document.documentElement;p.insertBefore(s,p.firstChild); }},0)";
                //添加翻译的按钮
                view.loadUrl("javascript:$('.scope-address').attr('id','MicrosoftTranslatorWidget')");
                //添加翻译的脚本
                view.loadUrl(insertJavaScript);*/
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
                Log.d("zzzz", 12344566 + "");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }
}
