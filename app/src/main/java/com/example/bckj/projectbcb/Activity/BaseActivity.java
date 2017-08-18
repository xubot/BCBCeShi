package com.example.bckj.projectbcb.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bckj.projectbcb.R;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ImageView img1= (ImageView) findViewById(R.id.img);
        onClike(img1);
        title.setText(name);
        toolbar.setBackgroundColor(color);
        toolbar.inflateMenu(menuitem);
        //toolbar.setNavigationIcon(img);
    }
    public void onClike(ImageView img1) {}
}
