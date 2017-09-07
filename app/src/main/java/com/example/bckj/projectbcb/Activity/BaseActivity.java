package com.example.bckj.projectbcb.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bckj.projectbcb.R;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zzz", "开启基类");
        initView();
        init();
        load();
        cheked();
    }
    public abstract void initView();
    protected abstract void init();
    public abstract void cheked();
    protected abstract void load();
    public void setToolBar(String name, int img, int color, int menuitem){
        //设置ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title= (TextView) findViewById(R.id.titleName);
        LinearLayout img1= (LinearLayout) findViewById(R.id.img);
        onClike(img1);
        title.setText(name);
        toolbar.setBackgroundColor(color);
        toolbar.getBackground().setAlpha(100);
        toolbar.inflateMenu(menuitem);
    }
    public void onClike(LinearLayout img1) {}
}
