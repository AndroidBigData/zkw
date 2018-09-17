package com.zjwam.zkw.personalcenter.job;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.JobHoldAdapter;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.mvp.presenter.JobHoldPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobHoldPresenter;
import com.zjwam.zkw.mvp.view.IJobHoldView;

import java.util.List;

public class JobHoldActivity extends BaseActivity implements IJobHoldView{
    private ImageView back,job_hold_nodata;
    private TextView title;
    private LRecyclerView job_hold_recyclerview;
    private JobHoldAdapter jobHoldAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter,max_items;
    private boolean isRefresh = true;
    private IJobHoldPresenter jobHoldPresenter;
    private int holdPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_hold);
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
        title.setText("职位收藏");
        jobHoldPresenter = new JobHoldPresenter(this,this);
        jobHoldAdapter = new JobHoldAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobHoldAdapter);
        job_hold_recyclerview.setAdapter(lRecyclerViewAdapter);
        job_hold_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        job_hold_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        job_hold_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        job_hold_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        job_hold_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                jobHoldPresenter.getHold(isRefresh, String.valueOf(page));
            }
        });
        job_hold_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter<max_items){
                    page++;
                    jobHoldPresenter.getHold(isRefresh, String.valueOf(page));
                }else {
                    job_hold_recyclerview.setNoMore(true);
                }
            }
        });
        job_hold_recyclerview.refresh();
        jobHoldAdapter.setHold(new JobHoldAdapter.Hold() {
            @Override
            public void cancelHold(int position) {
                holdPosition = position;
                jobHoldPresenter.cancelHold(String.valueOf(jobHoldAdapter.getDataList().get(holdPosition).getHold_id()));
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        job_hold_nodata = findViewById(R.id.job_hold_nodata);
        title = findViewById(R.id.title);
        job_hold_recyclerview = findViewById(R.id.job_hold_recyclerview);
    }

    @Override
    public void setHoldItem(List<JobHomeBean.Resume> data, int count) {
        max_items = count;
        if (count > 0){
            jobHoldAdapter.addAll(data);
            mCurrentCounter += data.size();
            job_hold_nodata.setVisibility(View.GONE);
        }else {
            job_hold_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        jobHoldAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        job_hold_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelHold() {
        jobHoldAdapter.remove(holdPosition);
    }
}
