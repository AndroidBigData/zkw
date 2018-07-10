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
import com.zjwam.zkw.fragment.personalcenter.MineAnswerFragment;
import com.zjwam.zkw.fragment.personalcenter.MineAskFragment;
import com.zjwam.zkw.util.Reflex;

import java.util.ArrayList;
import java.util.List;

public class CourseAnswerActivity extends BaseActivity {

    private List<String> titleList;
    private List<Fragment> fragmentList;
    private TabLayout courseanswer_tablayout;
    private ViewPager courseanswer_viewpager;
    private ImageView courseanswer_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_answer);
        initView();
    }

    private void initView() {
        courseanswer_tablayout = findViewById(R.id.courseanswer_tablayout);
        courseanswer_viewpager = findViewById(R.id.courseanswer_viewpager);
        courseanswer_back = findViewById(R.id.courseanswer_back);
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("我提问的");
        titleList.add("我回答的");
        fragmentList.add(new MineAskFragment());
        fragmentList.add(new MineAnswerFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        courseanswer_viewpager.setAdapter(viewPagerAdapter);
        courseanswer_tablayout.setupWithViewPager(courseanswer_viewpager);
        courseanswer_viewpager.setOffscreenPageLimit(2);
        Reflex.setReflex(courseanswer_tablayout,56);
        courseanswer_back.setOnClickListener(new View.OnClickListener() {
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