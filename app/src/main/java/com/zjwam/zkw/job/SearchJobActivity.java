package com.zjwam.zkw.job;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.callback.PermissionListener;
import com.zjwam.zkw.customview.CityPicker;
import com.zjwam.zkw.entity.City;
import com.zjwam.zkw.entity.HotCity;
import com.zjwam.zkw.entity.HotCityBean;
import com.zjwam.zkw.entity.LocateState;
import com.zjwam.zkw.entity.LocatedCity;
import com.zjwam.zkw.listener.OnPickListener;
import com.zjwam.zkw.mvp.presenter.SearchJobHotCityPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.ISearchJobHotCityPresenter;
import com.zjwam.zkw.mvp.view.ISearchJobHotCityView;
import com.zjwam.zkw.util.LocationCity;

import java.util.ArrayList;
import java.util.List;

public class SearchJobActivity extends BaseActivity implements ISearchJobHotCityView{

    private ImageView back,search_job_delete;
    private EditText search_job_text;
    private TextView title,job_city,job_hy,job_zy;
    private RelativeLayout search_job_city,search_job_hy,search_job_zy;
    private Button search_job;
    private String selectIndustry,selectProfession;
    private String city;
    private List<HotCity> hotCities;
    private ISearchJobHotCityPresenter searchJobHotCityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        initView();
        initData();
    }

    private void initData() {
        searchJobHotCityPresenter = new SearchJobHotCityPresenter(this,this);
        searchJobHotCityPresenter.getHotCity();
        city = getIntent().getExtras().getString("city");
        if (city.length()>0){
            job_city.setText(city);
        }else {
            city = null;
        }
        back.setOnClickListener(onClickListener);
        search_job_delete.setOnClickListener(onClickListener);
        search_job_city.setOnClickListener(onClickListener);
        search_job_hy.setOnClickListener(onClickListener);
        search_job_zy.setOnClickListener(onClickListener);
        search_job.setOnClickListener(onClickListener);
        title.setText("职位检索");
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.search_job_delete:
                    search_job_text.setText("");
                    break;
                case R.id.search_job_city:
                    showCityPicker();
                    break;
                case R.id.search_job_hy:
                    startActivityForResult(new Intent(getBaseContext(),IndustryChoiceActivity.class),1);
                    break;
                case R.id.search_job_zy:
                    startActivityForResult(new Intent(getBaseContext(),ProfessionChoiceActivity.class),2);
                    break;
                case R.id.search_job:
                    if (job_city.getText().toString().length()>0){
                        Bundle bundle = new Bundle();
                        bundle.putString("city",city);
                        bundle.putString("name",search_job_text.getText().toString());
                        bundle.putString("industry",selectIndustry);
                        bundle.putString("profession",selectProfession);
                        startActivity(new Intent(getBaseContext(),SearchJobDetailsActivity.class).putExtras(bundle));
                    }else {
                        error("请选择城市");
                    }

                    break;

            }
        }
    };

    private void initView() {
        back = findViewById(R.id.back);
        search_job_delete = findViewById(R.id.search_job_delete);
        search_job_text = findViewById(R.id.search_job_text);
        title = findViewById(R.id.title);
        job_city = findViewById(R.id.job_city);
        job_hy = findViewById(R.id.job_hy);
        job_zy = findViewById(R.id.job_zy);
        search_job_city = findViewById(R.id.search_job_city);
        search_job_hy = findViewById(R.id.search_job_hy);
        search_job_zy = findViewById(R.id.search_job_zy);
        search_job = findViewById(R.id.search_job);
    }

    private void showCityPicker(){
        if (city != null){
            CityPicker.getInstance().setLocatedCity(new LocatedCity(city));
        }else {
            CityPicker.getInstance().setLocatedCity(null);
        }
        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())
                .enableAnimation(true)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        if (data != null) {
                            city = data.getName();
                            job_city.setText(city);
                        }
                    }
                    @Override
                    public void onLocate() {

                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && requestCode == 1){
            job_hy.setText(data.getStringExtra("hy"));
            selectIndustry = data.getStringExtra("hyid");
        }else if (requestCode == 2 && requestCode == 2){
            job_zy.setText(data.getStringExtra("zy"));
            selectProfession = data.getStringExtra("zyid");
        }
    }

    @Override
    public void setHotCity(List<HotCityBean> list) {
        hotCities = new ArrayList<>();
        for (HotCityBean city : list) {
            hotCities.add(new HotCity(city.getCity()));
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

}
