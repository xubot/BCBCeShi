package com.example.bckj.projectbcb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bckj.projectbcb.R;

import java.util.Timer;
import java.util.TimerTask;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        final Intent intent=new Intent(this,NetActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,1500);
    }
}
