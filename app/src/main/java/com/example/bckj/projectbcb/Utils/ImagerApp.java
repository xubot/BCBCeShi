package com.example.bckj.projectbcb.Utils;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by Administrator on 2017/8/10.
 */

public class ImagerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        File cacheFile=new File(Environment.getExternalStorageDirectory().getPath()+"/ccc");
        //初使化ImageLoader
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)//设置缓存图片最大的宽度与高度
                .threadPoolSize(2)//设置线程池的大小
                .threadPriority(4)//设置优先级
                .memoryCacheSize(2*1024*1024)//设置内存缓存区的大小
                .diskCacheSize(20*1024*1024)//设置磁盘缓存区的大小
                .diskCache(new UnlimitedDiscCache(cacheFile))//自定义磁盘缓存路径
                .writeDebugLogs()//打印日志内容
                .build();
        ImageLoader.getInstance().init(config);
    }
}
