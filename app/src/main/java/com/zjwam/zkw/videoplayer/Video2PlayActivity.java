package com.zjwam.zkw.videoplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.design.widget.TabLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SimpleResponse;
import com.zjwam.zkw.entity.VideoMainMsgBean;
import com.zjwam.zkw.fragment.VideoPlayer.AnswerFragment;
import com.zjwam.zkw.fragment.VideoPlayer.CatalogFragment;
import com.zjwam.zkw.fragment.VideoPlayer.CommentFragment;
import com.zjwam.zkw.fragment.VideoPlayer.IntroduceFragment;
import com.zjwam.zkw.pay.PayPreviewActivity;
import com.zjwam.zkw.personalcenter.MineShopCartActivity;
import com.zjwam.zkw.util.AniManager;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.pay.PayResult;
import com.zjwam.zkw.util.Reflex;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Video2PlayActivity extends BaseActivity implements CatalogFragment.FragmentInteraction {
    private RelativeLayout video_back;
    private TextView video_title_name,video_price,video_old_price,video_buy,video_card_num;
    private String id = "", class_title = "",uid;
    private LandLayoutVideo detailPlayer;
    private TabLayout video_tablayout;
    private ViewPager video_viewpager;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private viewPagerAdapter adapter;
    private OrientationUtils orientationUtils;
    private boolean isPlay,isLogin;
    private boolean isPause,isBuy = false;
    private GSYVideoOptionBuilder gsyVideoOption;
    private String url;
    private String title;
    private ImageView video_jump_shopcard,video_sc;
    private ImageButton video_shopcart;
    private ConstraintLayout video_buyover;
    private VideoMainMsgBean video;
    private int isSC = 0;
    private Bundle bundle;

    private ImageView buyImg;// 界面上跑的小图片
    private AniManager mAniManager;//动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2_play);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        isLogin = ZkwPreference.getInstance(getBaseContext()).IsFlag();

        id = getIntent().getStringExtra("id");
        bundle = new Bundle();
        bundle.putString("id", id);
        class_title = getIntent().getStringExtra("title");
        if (class_title != null) {
            video_title_name.setText(class_title);
        }

        getFirstVideo();

/**
 * 初始化播放器
 */
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        video_sc.setOnClickListener(onClickListener);
        video_shopcart.setOnClickListener(onClickListener);
        video_back.setOnClickListener(onClickListener);
        video_jump_shopcard.setOnClickListener(onClickListener);
        video_buy.setOnClickListener(onClickListener);

        mAniManager = new AniManager();

    }

    private void setView() {

        titleList = new ArrayList<>();
        titleList.add("介绍");
        titleList.add("目录");

        fragmentList = new ArrayList<>();
        IntroduceFragment introduceFragment = new IntroduceFragment();
        introduceFragment.setArguments(bundle);
        fragmentList.add(introduceFragment);
        CatalogFragment catalogFragment = new CatalogFragment();
        catalogFragment.setArguments(bundle);
        fragmentList.add(catalogFragment);
        if (isBuy){
            titleList.add("问答");
            titleList.add("评价");
            AnswerFragment answerFragment = new AnswerFragment();
            answerFragment.setArguments(bundle);
            fragmentList.add(answerFragment);
            CommentFragment commentFragment = new CommentFragment();
            commentFragment.setArguments(bundle);
            fragmentList.add(commentFragment);
        }else {
            titleList.add("评价");
            CommentFragment commentFragment = new CommentFragment();
            commentFragment.setArguments(bundle);
            fragmentList.add(commentFragment);
        }

        adapter = new viewPagerAdapter(getSupportFragmentManager());
        video_viewpager.setAdapter(adapter);
        video_tablayout.setupWithViewPager(video_viewpager);
        if (isBuy){
            video_viewpager.setOffscreenPageLimit(4);
        }else {
            video_viewpager.setOffscreenPageLimit(3);
        }

        video_tablayout.setTabsFromPagerAdapter(adapter);
        video_tablayout.getTabAt(1).select();
        if (isBuy){
            Reflex.setReflex(video_tablayout,25);
        }else {
            Reflex.setReflex(video_tablayout,35);
        }

    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.video_back:
                    finish();
                    break;
                case R.id.video_sc:
                    if (isLogin){
                        setSCMsg();
                    }else {
                        Toast.makeText(getBaseContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.video_shopcart:
                    setShopCard();
                    break;
                case R.id.video_jump_shopcard:
                    if (isLogin){
                        startActivity(new Intent(getBaseContext(), MineShopCartActivity.class));
                    }else {
                        Toast.makeText(getBaseContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.video_buy:
                    if (isLogin){
                        Bundle bundle = new Bundle();
                        bundle.putString("clid",id);
                        bundle.putString("title",title);
                        Intent intent = new Intent(getBaseContext(),PayPreviewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getBaseContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    //启动动画
    public void startAnim(View v) {
        int[] end_location = new int[2];
        int[] start_location = new int[2];
        v.getLocationInWindow(start_location);// 获取购买按钮的在屏幕的X、Y坐标（动画开始的坐标）
        video_jump_shopcard.getLocationInWindow(end_location);// 这是用来存储动画结束位置，也就是购物车图标的X、Y坐标
        buyImg = new ImageView(this);// buyImg是动画的图片
        buyImg.setImageResource(R.drawable.plus_one);// 设置buyImg的图片

        //        mAniManager.setTime(5500);//自定义时间
        mAniManager.setAnim(this, buyImg, start_location, end_location);// 开始执行动画

        mAniManager.setOnAnimListener(new AniManager.AnimListener() {
            @Override
            public void setAnimBegin(AniManager a) {
                //动画开始时的监听
            }

            @Override
            public void setAnimEnd(AniManager a) {
                //动画结束后的监听
                video_card_num.setText(String.valueOf(video.getCar()+1));
                video_card_num.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setShopCard() {
        OkGo.<ResponseBean<SimpleResponse>>post(Config.URL + "api/user/car_add")
                .params("uid",uid)
                .params("id",id)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new Json2Callback<ResponseBean<SimpleResponse>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<SimpleResponse>> response) {
                        if (1 == response.body().code){
                            startAnim(video_shopcart);
                        }
                        Toast.makeText(getBaseContext(),response.body().msg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<ResponseBean<SimpleResponse>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setSCMsg() {
        OkGo.<ResponseBean<SimpleResponse>>post(Config.URL + "api/user/run_hold")
                .params("uid",uid)
                .params("id",id)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<SimpleResponse>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<SimpleResponse>> response) {
                        if (1 == isSC){
                            isSC = 0;
                            video_sc.setImageResource(R.drawable.video_sc);
                            Toast.makeText(getBaseContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                        }else if (0 == isSC){
                            isSC = 1;
                            video_sc.setImageResource(R.drawable.video_sc_over);
                            Toast.makeText(getBaseContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Response<ResponseBean<SimpleResponse>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getFirstVideo() {
        OkGo.<VideoMainMsgBean>get(Config.URL+"api/play/get_first_video")
                .params("id",id)
                .params("uid",uid)
                .cacheMode(CacheMode.NO_CACHE)
                .tag(this)
                .execute(new Json2Callback<VideoMainMsgBean>() {
                    @Override
                    public void onSuccess(Response<VideoMainMsgBean> response) {
                        video = response.body();
                        url = video.getAddress();
                        title = video.getVname();
                        ZkwPreference.getInstance(getBaseContext()).setVideoId(String.valueOf(video.getId()));
                        if (1 == video.getHold()){
                            isSC = 1;
                            video_sc.setImageResource(R.drawable.video_sc_over);
                        }
                        if (1 == video.getBuy()){
                            video_buyover.setVisibility(View.GONE);
                        }
                        video_price.setText("￥"+String.valueOf(video.getPrice()));
                        video_old_price.setText("原价 ￥"+String.valueOf(video.getOld_price()));
                        if (video.getCar()>0 && isLogin){
                            video_card_num.setText(String.valueOf(video.getCar()));
                            video_card_num.setVisibility(View.VISIBLE);
                        }else {
                            video_card_num.setVisibility(View.GONE);
                        }
                        if (1 == video.getBuy()){
                            isBuy = true;
                            video_buyover.setVisibility(View.GONE);
                            setView();
                        }else {
                            setView();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                            gsyVideoOption = new GSYVideoOptionBuilder();
                            gsyVideoOption
                                    .setIsTouchWiget(true)
                                    .setRotateViewAuto(false)
                                    .setLockLand(false)
                                    .setAutoFullWithSize(true)
                                    .setShowFullAnimation(false)
                                    .setNeedLockFull(true)
                                    .setCacheWithPlay(false)
                                    .setUrl(url)
                                    .setVideoTitle(title)
                                    .setVideoAllCallBack(new GSYSampleCallBack() {
                                        @Override
                                        public void onPrepared(String url, Object... objects) {
                                            Debuger.printfError("***** onPrepared **** " + objects[0]);
                                            Debuger.printfError("***** onPrepared **** " + objects[1]);
                                            super.onPrepared(url, objects);
                                            //开始播放了才能旋转和全屏
                                            orientationUtils.setEnable(true);
                                            isPlay = true;
                                        }

                                        @Override
                                        public void onEnterFullscreen(String url, Object... objects) {
                                            super.onEnterFullscreen(url, objects);
                                            Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                                            Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                                        }

                                        @Override
                                        public void onAutoComplete(String url, Object... objects) {
                                            super.onAutoComplete(url, objects);
                                        }

                                        @Override
                                        public void onClickStartError(String url, Object... objects) {
                                            super.onClickStartError(url, objects);
                                        }

                                        @Override
                                        public void onQuitFullscreen(String url, Object... objects) {
                                            super.onQuitFullscreen(url, objects);
                                            Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                                            Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                                            if (orientationUtils != null) {
                                                orientationUtils.backToProtVideo();
                                            }
                                        }
                                    })
                                    .setLockClickListener(new LockClickListener() {
                                        @Override
                                        public void onClick(View view, boolean lock) {
                                            if (orientationUtils != null) {
                                                //配合下方的onConfigurationChanged
                                                orientationUtils.setEnable(!lock);
                                            }
                                        }
                                    })
                                    .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                                        @Override
                                        public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                                            Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                                        }
                                    })
                                    .build(detailPlayer);
                            detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //直接横屏
                                    orientationUtils.resolveByClick();

                                    //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                                    detailPlayer.startWindowFullscreen(Video2PlayActivity.this, true, true);
                                }
                            });
                    }
                });
    }

    @Override
    public void process(String path, String title) {
        initViewVideo(path, title);
    }

    class viewPagerAdapter extends FragmentPagerAdapter {
        public viewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private void initView() {
        video_back = findViewById(R.id.video_back);
        video_title_name = findViewById(R.id.video_title_name);
        detailPlayer = findViewById(R.id.ad_player);
        video_tablayout = findViewById(R.id.video_tablayout);
        video_viewpager = findViewById(R.id.video_viewpager);
        //  video_price,video_old_price,video_buy;video_jump_shopcard,video_sc;video_shopcart
        video_price = findViewById(R.id.video_price);
        video_old_price = findViewById(R.id.video_old_price);
        video_jump_shopcard = findViewById(R.id.video_jump_shopcard);
        video_sc = findViewById(R.id.video_sc);
        video_buy = findViewById(R.id.video_buy);
        video_shopcart = findViewById(R.id.video_shopcart);
        video_buyover = findViewById(R.id.video_buyover);
        video_card_num = findViewById(R.id.video_card_num);
    }


    private void initViewVideo(String url, String title) {
        GSYVideoManager.clearAllDefaultCache(Video2PlayActivity.this);
        detailPlayer.onVideoReset();
        gsyVideoOption
                .setUrl(url)
                .setVideoTitle(title)
                .build(detailPlayer);
        detailPlayer.startPlayLogic();

    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        int time = getCurPlay().getCurrentPositionWhenPlaying();
        ZkwPreference.getInstance(getBaseContext()).setViteoTime(String.valueOf(time));
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        GSYVideoManager.clearAllDefaultCache(Video2PlayActivity.this);
        GSYVideoManager.releaseAllVideos();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 当前为横屏
            StatusBarUtil.hideFakeStatusBarView(Video2PlayActivity.this);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 当前为竖屏
            setStatusBar();
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return  detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

}
