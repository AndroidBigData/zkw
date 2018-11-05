package com.zjwam.zkw.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.R;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.AdvertisementBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.webview.WebViewActivity;

public class AdvertisementActivity extends BaseActivity {

    private TextView ad_jump;
    private RelativeLayout jump_webview;
    private String url = "", title = "";
    private TimeCount timeCount;
    private boolean isJump = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        init();
    }

    private void init() {
        ad_jump = findViewById(R.id.ad_jump);
        jump_webview = findViewById(R.id.jump_webview);
        ad_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeCount.onFinish();
                finish();
            }
        });
        jump_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isJump = true;
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("url", url);
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtras(bundle));
                finish();
            }
        });

        OkGo.<ResponseBean<AdvertisementBean>>get(Config.URL + "api/index/get_ads?type=android")
                .tag(this)
                .cacheKey("AdvertisementActivity")
                .execute(new JsonCallback<ResponseBean<AdvertisementBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<AdvertisementBean>> response) {
                        ResponseBean<AdvertisementBean> data = response.body();
                        url = data.data.getAd().getUrl();
                        title = data.data.getAd().getTitle();
                        Glide.with(getBaseContext())
                                .load(data.data.getAd().getImg())
                                .into(simpleTarget);
                    }

                    @Override
                    public void onCacheSuccess(Response<ResponseBean<AdvertisementBean>> response) {
                        super.onCacheSuccess(response);
                        onSuccess(response);
                    }

                    @Override
                    public void onError(Response<ResponseBean<AdvertisementBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            Toast.makeText(getBaseContext(), ((MyException) exception).getErrorBean().msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        timeCount = new TimeCount(4000, 1000);
        timeCount.start();

    }

    SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
        @Override
        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            jump_webview.setBackground(resource);
        }
    };


    class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            ad_jump.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            if (!isJump){
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                if(getIntent().getBundleExtra("jpush") != null){
                    intent.putExtra("jpush", getIntent().getBundleExtra("jpush"));
                }
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}
