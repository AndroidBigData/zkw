package com.zjwam.zkw.job;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.JobDetailsAdapter;
import com.zjwam.zkw.customview.FlowLayout;
import com.zjwam.zkw.entity.JobDetailsBean;
import com.zjwam.zkw.mvp.presenter.JobDeatailsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobDetailsPresenter;
import com.zjwam.zkw.mvp.view.IJobDetailsView;
import com.zjwam.zkw.util.GlideImageUtil;

public class JobDetailsActivity extends BaseActivity implements IJobDetailsView{

    private TextView title,submit_resume,job_dedails_title,job_dedails_hold,job_dedails_td,job_dedails_money,job_dedails_area,job_dedails_area_more,
            job_dedails_companyName,job_dedails_companyDetails,job_dedails_companyNum,job_dedails_describe,job_dedails_requirement,job_dedails_time;
    private FlowLayout job_dedails_type;
    private IJobDetailsPresenter jobDetailsPresenter;
    private String jobId;
    private ImageView back,job_dedails_companyImg;
    private LRecyclerView job_details_recyclerview;
    private View headerView;
    private JobDetailsBean jobDetailsBean;
    private JobDetailsAdapter jobDetailsAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private boolean isHold = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        initView();
        initData();
    }

    private void initData() {
        title.setText("职位详情");
        jobDetailsPresenter = new JobDeatailsPresenter(this,this);
        submit_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jobDetailsAdapter = new JobDetailsAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobDetailsAdapter);
        lRecyclerViewAdapter.addHeaderView(headerView);
        job_details_recyclerview.setAdapter(lRecyclerViewAdapter);
        job_details_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        job_details_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        job_details_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        job_details_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        job_details_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                jobDetailsPresenter.getJobDetails(jobId);
            }
        });
        job_details_recyclerview.setLoadMoreEnabled(false);
        job_details_recyclerview.refresh();
        job_dedails_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                job_dedails_hold.setEnabled(false);
                jobDetailsPresenter.holdJob(jobId, String.valueOf(jobDetailsBean.getCompany_id()));
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(jobDetailsAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getBaseContext(),JobDetailsActivity.class).putExtras(bundle));
                finish();
            }
        });
    }

    private void initView() {
        submit_resume = findViewById(R.id.submit_resume);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        job_details_recyclerview = findViewById(R.id.job_details_recyclerview);
        headerView = LayoutInflater.from(this).inflate(R.layout.job_details_header,(ViewGroup)findViewById(android.R.id.content),false);
        job_dedails_title = headerView.findViewById(R.id.job_dedails_title);
        job_dedails_money = headerView.findViewById(R.id.job_dedails_money);
        job_dedails_time =headerView.findViewById(R.id.job_dedails_time);
        job_dedails_describe = headerView.findViewById(R.id.job_dedails_describe);
        job_dedails_requirement = headerView.findViewById(R.id.job_dedails_requirement);
        job_dedails_hold = headerView.findViewById(R.id.job_dedails_hold);
        job_dedails_td = headerView.findViewById(R.id.job_dedails_td);
        job_dedails_companyImg = headerView.findViewById(R.id.job_dedails_companyImg);
        job_dedails_area = headerView.findViewById(R.id.job_dedails_area);
        job_dedails_area_more = headerView.findViewById(R.id.job_dedails_area_more);
        job_dedails_companyName = headerView.findViewById(R.id.job_dedails_companyName);
        job_dedails_companyDetails = headerView.findViewById(R.id.job_dedails_companyDetails);
        job_dedails_companyNum = headerView.findViewById(R.id.job_dedails_companyNum);
        job_dedails_type = headerView.findViewById(R.id.job_dedails_type);
        jobId = getIntent().getExtras().getString("id");
    }

    @Override
    public void setJobDetails(JobDetailsBean jobDetails) {
        jobDetailsBean = jobDetails;
        job_dedails_title.setText(jobDetailsBean.getJob_name());
        job_dedails_money.setText(jobDetailsBean.getSalary());
        job_dedails_time.setText(jobDetailsBean.getCreate_time());
        job_dedails_describe.setText(jobDetailsBean.getIntro());
        job_dedails_requirement.setText(jobDetailsBean.getRequirement());
        if (jobDetailsBean.getHold() == 1){
            isHold = true;
            isHold();
        }else {
            isHold = false;
            isUnHold();
        }
        job_dedails_td.setText(jobDetailsBean.getType());
        job_dedails_area.setText(jobDetailsBean.getWork_area());
        job_dedails_area_more.setText(jobDetailsBean.getAddress());
        GlideImageUtil.setImageView(this,jobDetailsBean.getLogo(),job_dedails_companyImg, RequestOptions.circleCropTransform());
        job_dedails_companyName.setText(jobDetailsBean.getCompany_name());
        job_dedails_companyDetails.setText(jobDetailsBean.getCompany_type());
        job_dedails_companyNum.setText("在招职位"+jobDetailsBean.getCount()+"个");
        initBenefit();
        jobDetailsAdapter.addAll(jobDetailsBean.getRecommend());
    }

    private void isHold(){
        job_dedails_hold.setBackground(getResources().getDrawable(R.drawable.hold));
        job_dedails_hold.setTextColor(getResources().getColor(R.color.colorAccent));
        job_dedails_hold.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.exam_hold),null,null,null);
    }
    private void isUnHold(){
        job_dedails_hold.setBackground(getResources().getDrawable(R.drawable.curriculun_checked_item_shape));
        job_dedails_hold.setTextColor(getResources().getColor(R.color.text_color_gray));
        job_dedails_hold.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.exam_unhold),null,null,null);
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
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        jobDetailsAdapter.clear();
    }

    @Override
    public void refreshComplete() {
        job_details_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void hold() {
        if (isHold){
            isHold = false;
            isUnHold();
        }else {
            isHold = true;
            isHold();
        }
    }

    @Override
    public void holdFinish() {
        job_dedails_hold.setEnabled(true);
    }

}
