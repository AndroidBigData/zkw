package com.zjwam.zkw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;

public class BaseActivity extends AppCompatActivity{

    private boolean mReceiverTag = false;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),60);
    }


    /**
     * 关闭Activity的广播
     */
    protected BroadcastReceiver finishAppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("exitapp");
        if (!mReceiverTag){
            this.registerReceiver(this.finishAppReceiver, filter);
            mReceiverTag = true;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiverTag){
            mReceiverTag = false;
            this.unregisterReceiver(this.finishAppReceiver);
        }

        OkGo.getInstance().cancelTag(this);
    }

    public void error(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
