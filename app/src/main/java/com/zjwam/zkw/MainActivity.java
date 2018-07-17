package com.zjwam.zkw;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.CurriculumLnitializationBean;
import com.zjwam.zkw.entity.HomePageBean;
import com.zjwam.zkw.entity.PersoanlMessage;
import com.zjwam.zkw.entity.PersonalQDBean;
import com.zjwam.zkw.fragment.CurriculumFragment;
import com.zjwam.zkw.fragment.HomePageFragment;
import com.zjwam.zkw.fragment.MineFragment;
import com.zjwam.zkw.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private LinearLayout index_home, index_curriculum, index_mine;
    private ImageView img_home, img_curriculum, img_mine;
    private TextView txt_home, txt_curriculum, txt_mine;
    private FragmentManager fragmentManager;
    private String EXITAPP = "再按一次退出程序";
    private long exitTime = 0;
    private Fragment currentFragment;
    private List<Fragment> fragments;
    private HomePageFragment homePageFragment;
    private CurriculumFragment curriculumFragment;
    private MineFragment mineFragment;
    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        if (savedInstanceState != null) { // “内存重启”时调用
            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);
            //注意，添加顺序要跟下面添加的顺序一样！！！！
            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0 + ""));
            fragments.add(fragmentManager.findFragmentByTag(1 + ""));
            fragments.add(fragmentManager.findFragmentByTag(2 + ""));
            //恢复fragment页面
            restoreFragment();
        } else {      //正常启动时调用
            fragments.add(homePageFragment);
            fragments.add(curriculumFragment);
            fragments.add(mineFragment);
            showFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT, currentIndex);
        super.onSaveInstanceState(outState);
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment() {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if (!fragments.get(currentIndex).isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.content, fragments.get(currentIndex), "" + currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag
        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }

        currentFragment = fragments.get(currentIndex);
        show(currentIndex);
        transaction.commit();
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment() {
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.size(); i++) {

            if (i == currentIndex) {
                mBeginTreansaction.show(fragments.get(i));
            } else {
                mBeginTreansaction.hide(fragments.get(i));
            }
        }

        mBeginTreansaction.commit();

        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        index_home.setOnClickListener(onClickListener);
        index_curriculum.setOnClickListener(onClickListener);
        index_mine.setOnClickListener(onClickListener);
        currentFragment = new Fragment();
        fragments = new ArrayList<>();
        homePageFragment = new HomePageFragment(this);
        curriculumFragment = new CurriculumFragment(this);
        mineFragment = new MineFragment(this);
        homePageFragment.setOnJumpListener(new HomePageFragment.onJumpListener() {
            @Override
            public void onClick(String wid, String id, String name) {
                Bundle bundle = new Bundle();
                bundle.putString("wid", wid);
                bundle.putString("id", id);
                bundle.putString("name", name);
                Log.i("---bundle:", wid + name + id);
                curriculumFragment.setArguments(bundle);
                currentIndex = 1;
                showFragment();
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.index_home:
                    currentIndex = 0;
                    break;
                case R.id.index_curriculum:
                    currentIndex = 1;
                    break;
                case R.id.index_mine:
                    currentIndex = 2;
                    break;
            }
            showFragment();
        }
    };

    private void initView() {
        index_home = findViewById(R.id.index_home);
        index_curriculum = findViewById(R.id.index_curriculum);
        index_mine = findViewById(R.id.index_mine);
        img_home = findViewById(R.id.img_home);
        img_curriculum = findViewById(R.id.img_curriculum);
        img_mine = findViewById(R.id.img_mine);
        txt_home = findViewById(R.id.txt_home);
        txt_curriculum = findViewById(R.id.txt_curriculum);
        txt_mine = findViewById(R.id.txt_mine);
    }

    private void show(int position) {
        img_home.setImageResource(position == 0 ? R.drawable.home_select
                : R.drawable.home);
        txt_home.setTextColor(getResources().getColor(
                position == 0 ? R.color.black : R.color.text_color_gray));
        img_curriculum.setImageResource(position == 1 ? R.drawable.curriculum_select
                : R.drawable.curriculum);
        txt_curriculum.setTextColor(getResources().getColor(
                position == 1 ? R.color.black : R.color.text_color_gray));
        img_mine.setImageResource(position == 2 ? R.drawable.mine_select
                : R.drawable.mine);
        txt_mine.setTextColor(getResources().getColor(
                position == 2 ? R.color.black : R.color.text_color_gray));
    }

    /**
     * 连续点击两次返回键退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), EXITAPP, Toast.LENGTH_SHORT).show();
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用程序的方法，发送退出程序的广播
     */
    private void exitApp() {
        Intent intent = new Intent();
        intent.setAction("exitapp");
        this.sendBroadcast(intent);
    }

    /**
     * 主页
     */
    public void loadData(Response<HomePageBean> response) {
        homePageFragment.loadData(response);
    }

    /**
     * 课程页面
     */
    public void getChoiceData(Response<ClassSearchBean> response) {
        curriculumFragment.getChoiceData(response);
    }
    public void getChoiceDatFinish() {
        curriculumFragment.getChoiceDatFinish();
    }
    public void getChoiceListData(Response<CurriculumLnitializationBean> response){
        curriculumFragment.getChoiceListData(response);
    }
    public void getChoiceListDataFinish(){
        curriculumFragment.getChoiceListDataFinish();
    }
    public void getData(Response<CurriculumLnitializationBean> response){
        curriculumFragment.getData(response);
    }
    public void getDataFinish(){
        curriculumFragment.getDataFinish();
    }
    public void getInitialization(Response<CurriculumLnitializationBean> response){
        curriculumFragment.getInitialization(response);
    }
    /**
     * 个人中心
     */
    public void getPersonalMessage(Response<PersoanlMessage> response){
        mineFragment.getPersonalMessage(response);
    }
    public void qdMessage(Response<PersonalQDBean> response){
        mineFragment.qdMessage(response);
    }
    public void qdMessageError(Response<PersonalQDBean> response){
        mineFragment.qdMessageError(response);
    }
    public void qdMessageFinish(){
        mineFragment.qdMessageFinish();
    }
}
