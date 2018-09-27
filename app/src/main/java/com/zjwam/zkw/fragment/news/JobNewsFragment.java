package com.zjwam.zkw.fragment.news;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.NewsAdapter;
import com.zjwam.zkw.adapter.TeacherMoreRYAdapter;
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.mvp.presenter.JobNewsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobNewsPresenter;
import com.zjwam.zkw.mvp.view.IJobNewsView;
import com.zjwam.zkw.news.NewsActivity;
import com.zjwam.zkw.news.NewsMoreActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobNewsFragment extends Fragment implements IJobNewsView {
    private Context context;
    private RecyclerView mqtj_recyclerview;
    private TeacherMoreRYAdapter adapter;
    private LRecyclerView job_qzzx_recyclerview,job_zpzx_recyclerview,job_mqzx_recyclerview;
    private TextView job_qzzx_more,job_zpzx_more,job_mqzx_more;
    private LRecyclerViewAdapter qzzxRecyclerViewAdapter,zpzxRecyclerViewAdapter,mqzxRecyclerViewAdapter;
    private NewsAdapter qzzxAdapter,zpzxAdapter,mqzxAdapter;
    private IJobNewsPresenter jobNewsPresenter;
    public JobNewsFragment() {
        // Required empty public constructor
    }

    public static JobNewsFragment newInstance(Context context) {

        JobNewsFragment fragment = new JobNewsFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        jobNewsPresenter = new JobNewsPresenter(context,this);
        jobNewsPresenter.getNews("");
        qzzxAdapter = new NewsAdapter(context);
        zpzxAdapter = new NewsAdapter(context);
        mqzxAdapter = new NewsAdapter(context);
        qzzxRecyclerViewAdapter = new LRecyclerViewAdapter(qzzxAdapter);
        zpzxRecyclerViewAdapter = new LRecyclerViewAdapter(zpzxAdapter);
        mqzxRecyclerViewAdapter = new LRecyclerViewAdapter(mqzxAdapter);
        job_qzzx_recyclerview.setAdapter(qzzxRecyclerViewAdapter);
        job_zpzx_recyclerview.setAdapter(zpzxRecyclerViewAdapter);
        job_mqzx_recyclerview.setAdapter(mqzxRecyclerViewAdapter);
        job_qzzx_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        job_zpzx_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        job_mqzx_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        job_qzzx_recyclerview.setPullRefreshEnabled(false);
        job_qzzx_recyclerview.setLoadMoreEnabled(false);
        job_zpzx_recyclerview.setPullRefreshEnabled(false);
        job_zpzx_recyclerview.setLoadMoreEnabled(false);
        job_mqzx_recyclerview.setPullRefreshEnabled(false);
        job_mqzx_recyclerview.setLoadMoreEnabled(false);
        job_qzzx_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qzzxAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(qzzxAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","求职资讯");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        job_zpzx_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zpzxAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(zpzxAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","招聘资讯");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        job_mqzx_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mqzxAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(zpzxAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","名企资讯");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        adapter = new TeacherMoreRYAdapter(context,null);
        mqtj_recyclerview.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider_white));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mqtj_recyclerview.addItemDecoration(divider);
        mqtj_recyclerview.setLayoutManager(linearLayoutManager);
    }

    private void initView() {
        job_qzzx_recyclerview = getActivity().findViewById(R.id.job_qzzx_recyclerview);
        job_zpzx_recyclerview = getActivity().findViewById(R.id.job_zpzx_recyclerview);
        job_mqzx_recyclerview = getActivity().findViewById(R.id.job_mqzx_recyclerview);
        job_qzzx_more = getActivity().findViewById(R.id.job_qzzx_more);
        job_zpzx_more = getActivity().findViewById(R.id.job_zpzx_more);
        job_mqzx_more = getActivity().findViewById(R.id.job_mqzx_more);
        mqtj_recyclerview = getActivity().findViewById(R.id.mqtj_recyclerview);
    }

    @Override
    public void setNews(List<NewsBean> qzzx, List<NewsBean> zpzx,List<NewsBean> mqzx,List<String> mqtj) {
        qzzxAdapter.addAll(qzzx);
        zpzxAdapter.addAll(zpzx);
        mqzxAdapter.addAll(mqzx);
        adapter.addAll(mqtj);
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof NewsActivity){
            ((NewsActivity) context).error(msg);
        }
    }
}
