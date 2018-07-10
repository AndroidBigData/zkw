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

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
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
        fragmentList.add(new LearnAllFragment());
        fragmentList.add(new LearningFragment());
        fragmentList.add(new LearnOverFragment());
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
