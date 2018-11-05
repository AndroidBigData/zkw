package com.zjwam.zkw.personalcenter.job;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.FlowLayout;
import com.zjwam.zkw.entity.JobDetailsBean;
import com.zjwam.zkw.mvp.presenter.MineDeliveryPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMineDeliveryPresenter;
import com.zjwam.zkw.mvp.view.IMineDeliveryView;
import com.zjwam.zkw.util.GlideImageUtil;

public class MineDeliveryActivity extends BaseActivity implements IMineDeliveryView{
    private TextView title,job_dedails_title,job_dedails_hold,job_dedails_td,job_dedails_money,job_dedails_area,job_dedails_area_more,
            job_dedails_companyName,job_dedails_companyDetails,job_dedails_companyNum,job_dedails_describe,job_dedails_requirement,job_dedails_time;
    private FlowLayout job_dedails_type;
    private String id;
    private ImageView back,job_dedails_companyImg;
    private JobDetailsBean jobDetailsBean;
    private IMineDeliveryPresenter deliveryPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_delivery);
        initView();
        initData();
    }

    private void initData() {
        id = getIntent().getExtras().getString("id");
        title.setText("申请职位详情");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        deliveryPresenter = new MineDeliveryPresenter(this,this);
        deliveryPresenter.getDelivery(id);
    }

    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        job_dedails_title = findViewById(R.id.job_dedails_title);
        job_dedails_money = findViewById(R.id.job_dedails_money);
        job_dedails_time =findViewById(R.id.job_dedails_time);
        job_dedails_describe = findViewById(R.id.job_dedails_describe);
        job_dedails_requirement = findViewById(R.id.job_dedails_requirement);
        job_dedails_hold = findViewById(R.id.job_dedails_hold);
        job_dedails_td = findViewById(R.id.job_dedails_td);
        job_dedails_companyImg = findViewById(R.id.job_dedails_companyImg);
        job_dedails_area = findViewById(R.id.job_dedails_area);
        job_dedails_area_more = findViewById(R.id.job_dedails_area_more);
        job_dedails_companyName = findViewById(R.id.job_dedails_companyName);
        job_dedails_companyDetails = findViewById(R.id.job_dedails_companyDetails);
        job_dedails_companyNum = findViewById(R.id.job_dedails_companyNum);
        job_dedails_type = findViewById(R.id.job_dedails_type);
    }

    private void initBenefit() {
        job_dedails_type.removeAllViews();
        for (int i = 0; i < jobDetailsBean.getBenefit().size(); i++) {
            JobDetailsBean.Benefit option = jobDetailsBean.getBenefit().get(i);
            TextView checkboxView = (TextView) LayoutInflater.from(this).inflate(R.layout.job_benefit, job_dedails_type,false);
            checkboxView.setText("  "+option.getType()+"  ");
            job_dedails_type.addView(checkboxView);
        }
    }

    @Override
    public void setJobDetails(JobDetailsBean jobDetails) {
        jobDetailsBean = jobDetails;
        job_dedails_title.setText(jobDetailsBean.getJob_name());
        job_dedails_money.setText(jobDetailsBean.getSalary());
        job_dedails_time.setText(jobDetailsBean.getCreate_time());
        job_dedails_describe.setText(jobDetailsBean.getIntro());
        job_dedails_requirement.setText(jobDetailsBean.getRequirement());
        job_dedails_td.setText(jobDetailsBean.getType());
        job_dedails_area.setText(jobDetailsBean.getWork_area());
        job_dedails_area_more.setText(jobDetailsBean.getAddress());
        GlideImageUtil.setImageView(this,jobDetailsBean.getLogo(),job_dedails_companyImg, RequestOptions.circleCropTransform());
        job_dedails_companyName.setText(jobDetailsBean.getCompany_name());
        job_dedails_companyDetails.setText(jobDetailsBean.getCompany_type());
        job_dedails_companyNum.setText("在招职位"+jobDetailsBean.getCount()+"个");
        initBenefit();
    }

    @Override
    public void showMsg(String msg) {

    }
}
