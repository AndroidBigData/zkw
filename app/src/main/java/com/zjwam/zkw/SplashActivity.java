package com.zjwam.zkw;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;

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
        startActivity(intent);
        finish();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}
