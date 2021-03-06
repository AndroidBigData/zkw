package com.zjwam.zkw.job;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.JobRecommendAdapter;
import com.zjwam.zkw.callback.PermissionListener;
import com.zjwam.zkw.customview.CityPicker;
import com.zjwam.zkw.entity.City;
import com.zjwam.zkw.entity.HotCity;
import com.zjwam.zkw.entity.HotCityBean;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.LocatedCity;
import com.zjwam.zkw.listener.OnPickListener;
import com.zjwam.zkw.mvp.presenter.JobHomePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobHomePresenter;
import com.zjwam.zkw.mvp.view.IJobHomeView;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.ArrayList;
import java.util.List;

public class JobHomePageActivity extends BaseActivity implements IJobHomeView {

    private TextView job_hr, job_zd, job_test, job_gh, search_fl;
    private LinearLayout search_title_job;
    private ImageView job_home_back, job_home_nodata;
    private LRecyclerView job_home_recycler;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private JobRecommendAdapter jobRecommendAdapter;
    private int mCurrentCounter, max_items, page;
    private boolean isRefresh;
    private IJobHomePresenter jobHomePresenter;
    private String city,loadCity,choiceCity;
    private List<HotCity> hotCities;
    private LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_home_page);
        initView();
        initData();
    }

    private void initData() {
        jobHomePresenter = new JobHomePresenter(this, this);
        job_hr.setOnClickListener(onClickListener);
        job_zd.setOnClickListener(onClickListener);
        job_test.setOnClickListener(onClickListener);
        job_gh.setOnClickListener(onClickListener);
        search_fl.setOnClickListener(onClickListener);
        job_home_back.setOnClickListener(onClickListener);
        search_title_job.setOnClickListener(onClickListener);
        jobRecommendAdapter = new JobRecommendAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobRecommendAdapter);
        job_home_recycler.setAdapter(lRecyclerViewAdapter);
        job_home_recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        job_home_recycler.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        job_home_recycler.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        job_home_recycler.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        job_home_recycler.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                jobHomePresenter.getJobItem(String.valueOf(page),search_fl.getText().toString(), isRefresh);
            }
        });
        job_home_recycler.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    jobHomePresenter.getJobItem(String.valueOf(page),search_fl.getText().toString(), isRefresh);
                } else {
                    job_home_recycler.setNoMore(true);
                }
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(jobRecommendAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getBaseContext(), JobDetailsActivity.class).putExtras(bundle));
            }
        });

        jobHomePresenter.getHotCity();

        MyLocationListenner myListener = new MyLocationListenner();
        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(myListener);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission();
        }else {
//            loadCity = ZkwPreference.getInstance(getBaseContext()).getCity();
//            if (loadCity != null&&loadCity.length()>0){
//                search_fl.setText(loadCity);
//                job_home_recycler.refresh();
//            }else if (city != null&&city.length()>0){
//                search_fl.setText(city);
//                ZkwPreference.getInstance(getBaseContext()).setCity(city);
//                job_home_recycler.refresh();
//            }else {
//                if (hotCities.size()>0){
//                    showCityPicker();
//                }
//            }
            mLocationClient.start();
        }

    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }
    private class MyLocationListenner extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                //当前设备位置所在的市
                city = location.getCity();
                loadCity = ZkwPreference.getInstance(getBaseContext()).getCity();
                if (loadCity != null&&loadCity.length()>0){
                    search_fl.setText(loadCity);
                    CityPicker.getInstance().setLocatedCity(new LocatedCity(loadCity));
                    job_home_recycler.refresh();
                }else if (city != null&&city.length()>0){
                    search_fl.setText(city);
                    ZkwPreference.getInstance(getBaseContext()).setCity(city);
                    CityPicker.getInstance().setLocatedCity(new LocatedCity(city));
                    job_home_recycler.refresh();
                }else {
                    if (hotCities.size()>0){
                        showCityPicker();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.job_hr:
                    break;
                case R.id.job_zd:
                    startActivity(new Intent(getBaseContext(),JobEmploymentActivity.class));
                    break;
                case R.id.job_test:
                    break;
                case R.id.job_gh:
                    Bundle bundle = new Bundle();
                    bundle.putString("city",search_fl.getText().toString());
                    startActivity(new Intent(getBaseContext(),SearchJobActivity.class).putExtras(bundle));
                    break;
                case R.id.search_fl:
                    showCityPicker();
                    break;
                case R.id.job_home_back:
                    finish();
                    break;
                case R.id.search_title_job:
                    if (search_fl.getText().toString().length()>0){
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("city",search_fl.getText().toString());
                        startActivity(new Intent(getBaseContext(),SearchJobDetailsActivity.class).putExtras(bundle1));
                    }else {
                        error("请选择城市！");
                        showCityPicker();
                    }
                    break;
            }
        }
    };

    private void initView() {
        job_hr = findViewById(R.id.job_hr);
        job_zd = findViewById(R.id.job_zd);
        job_test = findViewById(R.id.job_test);
        job_gh = findViewById(R.id.job_gh);
        search_fl = findViewById(R.id.search_fl);
        job_home_back = findViewById(R.id.job_home_back);
        job_home_recycler = findViewById(R.id.job_home_recycler);
        job_home_nodata = findViewById(R.id.job_home_nodata);
        search_title_job = findViewById(R.id.search_title_job);
    }

    @Override
    public void addJobItem(List<JobHomeBean.Resume> list, int count) {
        max_items = count;
        if (max_items > 0) {
            jobRecommendAdapter.addAll(list);
            mCurrentCounter += list.size();
            job_home_nodata.setVisibility(View.GONE);
        } else {
            job_home_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        jobRecommendAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        job_home_recycler.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void setHotCity(List<HotCityBean> list) {
        hotCities = new ArrayList<>();
        for (HotCityBean city : list) {
            hotCities.add(new HotCity(city.getCity()));
        }

    }

    private void showCityPicker(){
        if (loadCity != null&&loadCity.length()>0){
            CityPicker.getInstance().setLocatedCity(new LocatedCity(loadCity));
        }else if (city != null&&city.length()>0){
            CityPicker.getInstance().setLocatedCity(new LocatedCity(city));
        } else {
            CityPicker.getInstance().setLocatedCity(null);
        }
        if (!JobHomePageActivity.this.isFinishing()){
            CityPicker.getInstance()
                    .setFragmentManager(getSupportFragmentManager())
                    .enableAnimation(true)
                    .setHotCities(hotCities)
                    .setOnPickListener(new OnPickListener() {
                        @Override
                        public void onPick(int position, City data) {
                            if (data != null) {
                                choiceCity = data.getName();
                                search_fl.setText(choiceCity);
                                ZkwPreference.getInstance(getBaseContext()).setCity(choiceCity);
                                job_home_recycler.refresh();
                            }
                        }
                        @Override
                        public void onLocate() {
//                            city = new LocationCity(JobHomePageActivity.this).getLocation();
                            mLocationClient.restart();

                        }
                    })
                    .show();
        }

    }

    private void permission() {
        requestRunPermisssion(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
//                city = new LocationCity(JobHomePageActivity.this).getLocation();
                mLocationClient.start();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                if (hotCities.size()>0){
                    showCityPicker();
                }
            }
        });
    }
}
