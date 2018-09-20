package com.zjwam.zkw.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.fragment.news.BusinessNewsFragment;
import com.zjwam.zkw.fragment.news.ClassNewsFragment;
import com.zjwam.zkw.fragment.news.JobNewsFragment;
import com.zjwam.zkw.fragment.news.MainNewsFragment;
import com.zjwam.zkw.fragment.news.TestNewsFragment;
import com.zjwam.zkw.util.Reflex;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity {

    private List<String> titleList;
    private List<Fragment> fragmentList;
    private TabLayout news_tablayout;
    private ViewPager news_viewpager;
    private MainNewsFragment mainNewsFragment;
    private ClassNewsFragment classNewsFragment;
    private TestNewsFragment testNewsFragment;
    private JobNewsFragment jobNewsFragment;
    private BusinessNewsFragment businessNewsFragment;
    private TextView title;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initData();
    }

    private void initData() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("新闻中心");
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("今日头条");
        titleList.add("课程资讯");
        titleList.add("考试资讯");
        titleList.add("求职招聘");
        titleList.add("创业资讯");
        mainNewsFragment = MainNewsFragment.newInstance(this);
        fragmentList.add(mainNewsFragment);
        classNewsFragment = ClassNewsFragment.newInstance(this);
        fragmentList.add(classNewsFragment);
        testNewsFragment = TestNewsFragment.newInstance(this);
        fragmentList.add(testNewsFragment);
        jobNewsFragment = JobNewsFragment.newInstance(this);
        fragmentList.add(jobNewsFragment);
        businessNewsFragment = BusinessNewsFragment.newInstance(this);
        fragmentList.add(businessNewsFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        news_viewpager.setAdapter(viewPagerAdapter);
        news_tablayout.setupWithViewPager(news_viewpager);
        news_viewpager.setOffscreenPageLimit(5);
        Reflex.setReflex(news_tablayout,16);

    }

    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        news_tablayout = findViewById(R.id.news_tablayout);
        news_viewpager = findViewById(R.id.news_viewpager);
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
