package com.zjwam.zkw.personalcenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineClassBean;
import com.zjwam.zkw.fragment.personalcenter.LearnAllFragment;
import com.zjwam.zkw.fragment.personalcenter.LearnOverFragment;
import com.zjwam.zkw.fragment.personalcenter.LearningFragment;
import com.zjwam.zkw.util.Reflex;

import java.util.ArrayList;
import java.util.List;

public class MineClassActivity extends BaseActivity {

    private List<String> titleList;
    private List<Fragment> fragmentList;
    private TabLayout mine_class_tablayout;
    private ViewPager mine_class_viewpager;
    private ImageView mine_class_back;
    private LearnAllFragment learnAllFragment;
    private LearningFragment learningFragment;
    private LearnOverFragment learnOverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_class);
        initView();
    }

    private void initView() {
        mine_class_tablayout = findViewById(R.id.mine_class_tablayout);
        mine_class_viewpager = findViewById(R.id.mine_class_viewpager);
        mine_class_back = findViewById(R.id.mine_class_back);
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("学习中");
        titleList.add("已完成");
        learnAllFragment = new LearnAllFragment(this);
        fragmentList.add(learnAllFragment);
        learningFragment = LearningFragment.newInstance(this);
        fragmentList.add(learningFragment);
        learnOverFragment = LearnOverFragment.newInstance(this);
        fragmentList.add(learnOverFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mine_class_viewpager.setAdapter(viewPagerAdapter);
        mine_class_tablayout.setupWithViewPager(mine_class_viewpager);
        mine_class_viewpager.setOffscreenPageLimit(3);
        Reflex.setReflex(mine_class_tablayout,35);
        mine_class_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getLearnAll(Response<MineClassBean> response){
        learnAllFragment.getLearnAll(response);
    }
    public void getLearnAllError(Response<MineClassBean> response){
        learnAllFragment.getLearnAllError(response);
    }
    public void getLearnAllFinish(){
        learnAllFragment.getLearnAllFinish();
    }

    public void getLearning(Response<MineClassBean> response){
        learningFragment.getLearning(response);
    }
    public void getLearningError(Response<MineClassBean> response){
        learningFragment.getLearningError(response);
    }
    public void getLearningFinish(){
        learningFragment.getLearningFinish();
    }

    public void getLearnOver(Response<MineClassBean> response){
        learnOverFragment.getLearnOver(response);
    }
    public void getLearnOverError(Response<MineClassBean> response){
        learnOverFragment.getLearnOverError(response);
    }
    public void getLearnOverFinish(){
        learnOverFragment.getLearnOverFinish();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
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
}
