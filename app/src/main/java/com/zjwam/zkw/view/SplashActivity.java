package com.zjwam.zkw.view;

import android.content.Intent;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.zjwam.zkw.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                gotoMainPage();
            }
        };
        timer.schedule(task, 2000);
    }

    private void gotoMainPage() {
        Intent intent = (new Intent(this, AdvertisementActivity.class));
        if(getIntent().getBundleExtra("jpush") != null){
            intent.putExtra("jpush", getIntent().getBundleExtra("jpush"));
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}
