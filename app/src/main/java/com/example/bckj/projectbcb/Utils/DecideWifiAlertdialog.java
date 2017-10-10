package com.example.bckj.projectbcb.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2017/10/9.
 */

public class DecideWifiAlertdialog {
    public void netAlertdialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置对话框标题
        builder.setTitle("警告");
        //设置对话框内的文本
        builder.setMessage("WiFi找不到或者WiFi连接不正确，请查看WiFi是否连接正确");
        //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 执行点击确定按钮的业务逻辑
            }
        });
        //使用builder创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        //显示对话框
        dialog.show();
    }

}
