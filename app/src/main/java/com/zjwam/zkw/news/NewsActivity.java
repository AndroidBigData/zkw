package com.zjwam.zkw.news;

import android.os.Handler;
import android.os.Message;
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

import com.google.gson.Gson;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.JsonBean;
import com.zjwam.zkw.fragment.news.BusinessNewsFragment;
import com.zjwam.zkw.fragment.news.ClassNewsFragment;
import com.zjwam.zkw.fragment.news.JobNewsFragment;
import com.zjwam.zkw.fragment.news.MainNewsFragment;
import com.zjwam.zkw.fragment.news.TestNewsFragment;
import com.zjwam.zkw.util.BasicPickerView;
import com.zjwam.zkw.util.GetJsonDataUtil;
import com.zjwam.zkw.util.Reflex;

import org.json.JSONArray;

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
    private TextView title,news_city;
    private ImageView back;
    private List<JsonBean> province_item;
    private List<List<String>> city_item;
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private GetCityJob getCityJob;
    private GetCityTest getCityTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initData();
    }

    private void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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
        news_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 2||position == 3){
                    news_city.setVisibility(View.VISIBLE);
                }else {
                    news_city.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        news_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasicPickerView pickerView = new BasicPickerView();
                pickerView.pickerVIew(NewsActivity.this,"",province_item,city_item,null);
                pickerView.getSelctor(new BasicPickerView.Selctor() {
                    @Override
                    public void Options(int options1, int options2, int options3, View view) {
                        news_city.setText(" "+city_item.get(options1).get(options2));
                        getCityJob.citys(news_city.getText().toString());
                        getCityTest.citys(news_city.getText().toString());
                    }
                });
            }
        });
    }

    public void GetCityListenerJob(GetCityJob getCityJob){
        this.getCityJob = getCityJob;
    }

    public void GetCityListenerTest(GetCityTest getCityTest){
        this.getCityTest = getCityTest;
    }

    public interface GetCityJob{
        void citys(String city);
    }

    public interface GetCityTest{
        void citys(String city);
    }

    private void initJsonData() {//解析数据

        province_item = new ArrayList<>();
        city_item = new ArrayList<>();

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getBaseContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        province_item = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
            }

            /**
             * 添加城市数据
             */
            city_item.add(CityList);

        }

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
//                case MSG_LOAD_SUCCESS:
//                    break;
//                case MSG_LOAD_FAILED:
//                    break;
            }
        }
    };

    private void initView() {
        title = findViewById(R.id.title);
        news_city = findViewById(R.id.news_city);
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
