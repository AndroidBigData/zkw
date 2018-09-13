package com.zjwam.zkw.personalcenter.job;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.fragment.personalcenter.DeliveryFragment;
import com.zjwam.zkw.fragment.personalcenter.ResumeFragment;
import com.zjwam.zkw.util.Reflex;

import java.util.ArrayList;
import java.util.List;

public class MineJobActivity extends BaseActivity {

    private TabLayout mine_job_tab;
    private TextView title;
    private ImageView back;
    private ResumeFragment resumeFragment;
    private DeliveryFragment deliveryFragment;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager mine_job_viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_job);
        initView();
        initData();
    }

    private void initData() {
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("简历");
        titleList.add("投递");
        resumeFragment = ResumeFragment.newInstance(this);
        fragmentList.add(resumeFragment);
        deliveryFragment = DeliveryFragment.newInstance(this);
        fragmentList.add(deliveryFragment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mine_job_viewpager.setAdapter(viewPagerAdapter);
        mine_job_tab.setupWithViewPager(mine_job_viewpager);
        mine_job_viewpager.setOffscreenPageLimit(2);
        Reflex.setReflex(mine_job_tab,76);
        title.setText("我的求职");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        mine_job_tab = findViewById(R.id.mine_job_tab);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        mine_job_viewpager = findViewById(R.id.mine_job_viewpager);
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
