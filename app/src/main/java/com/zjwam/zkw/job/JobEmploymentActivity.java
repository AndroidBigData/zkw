package com.zjwam.zkw.job;

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
import com.zjwam.zkw.adapter.JobEmploymentAdapter;
import com.zjwam.zkw.entity.JobEmplomentBean;
import com.zjwam.zkw.mvp.presenter.JobEmplomentPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobEmplomentPresenter;
import com.zjwam.zkw.mvp.view.IJobEmploymentView;

import java.util.List;

public class JobEmploymentActivity extends BaseActivity implements IJobEmploymentView {

    private ImageView back,employment_noData;
    private TextView title;
    private LRecyclerView employment_recyclerview;
    private int page = 1,mCurrentCounter,max_items;
    private boolean isRefresh = true;
    private JobEmploymentAdapter jobEmploymentAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private IJobEmplomentPresenter emplomentPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_employment);
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
        title.setText("就业指导");
        emplomentPresenter = new JobEmplomentPresenter(this,this);
        jobEmploymentAdapter = new JobEmploymentAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobEmploymentAdapter);
        employment_recyclerview.setAdapter(lRecyclerViewAdapter);
        employment_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        employment_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        employment_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        employment_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        employment_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                emplomentPresenter.getEmploment(isRefresh, String.valueOf(page));
            }
        });
        employment_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter<max_items){
                    page++;
                    emplomentPresenter.getEmploment(isRefresh, String.valueOf(page));
                }else {
                    employment_recyclerview.setNoMore(true);
                }
            }
        });
        employment_recyclerview.refresh();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        employment_recyclerview = findViewById(R.id.employment_recyclerview);
        employment_noData = findViewById(R.id.employment_noData);
    }

    @Override
    public void addEmploymentItem(List<JobEmplomentBean.Guide> data, int count) {
        max_items = count;
        if (max_items>0){
            jobEmploymentAdapter.addAll(data);
            mCurrentCounter += data.size();
            employment_noData.setVisibility(View.GONE);
        }else {
            employment_noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        jobEmploymentAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        employment_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
