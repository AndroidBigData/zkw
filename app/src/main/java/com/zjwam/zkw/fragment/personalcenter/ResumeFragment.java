package com.zjwam.zkw.fragment.personalcenter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.MineJobResumeAdapter;
import com.zjwam.zkw.entity.MineJobResumeBean;
import com.zjwam.zkw.mvp.presenter.MineJobResumePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMineJobResumePresenter;
import com.zjwam.zkw.mvp.view.IMineJobResumeView;
import com.zjwam.zkw.personalcenter.job.CreateResumeActivity;
import com.zjwam.zkw.personalcenter.job.MineJobActivity;
import com.zjwam.zkw.personalcenter.job.ResumeDetailsActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment implements IMineJobResumeView{
    private Context context;
    private LRecyclerView resume_recyclerview;
    private MineJobResumeAdapter jobResumeAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ImageView resume_nodata;
    private IMineJobResumePresenter mineJobResumePresenter;
    private FloatingActionButton resume_fab;

    public ResumeFragment() {
        // Required empty public constructor
    }

    public static ResumeFragment newInstance(Context context) {
        ResumeFragment fragment = new ResumeFragment();
        fragment.context = context;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mineJobResumePresenter = new MineJobResumePresenter(context,this);
        jobResumeAdapter = new MineJobResumeAdapter(context);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobResumeAdapter);
        resume_recyclerview.setAdapter(lRecyclerViewAdapter);
        resume_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        resume_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        resume_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        resume_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        resume_recyclerview.setLoadMoreEnabled(false);
        resume_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mineJobResumePresenter.getJobResume();
            }
        });
        resume_recyclerview.refresh();
        jobResumeAdapter.setResumeClick(new MineJobResumeAdapter.ResumeClick() {
            @Override
            public void deleteResume(int position) {
                if (context instanceof MineJobActivity){
                    ((MineJobActivity) context).error(String.valueOf(position));
                }
            }

            @Override
            public void editResume(int position) {
                if (context instanceof MineJobActivity){
                    ((MineJobActivity) context).error(String.valueOf(position));
                }
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(jobResumeAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getActivity(), ResumeDetailsActivity.class).putExtras(bundle));
            }
        });
        resume_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateResumeActivity.class));
            }
        });
    }

    private void initView() {
        resume_recyclerview = getActivity().findViewById(R.id.resume_recyclerview);
        resume_nodata = getActivity().findViewById(R.id.resume_nodata);
        resume_fab = getActivity().findViewById(R.id.resume_fab);
    }

    @Override
    public void setResume(List<MineJobResumeBean> list) {
        if (list.size()>0){
            jobResumeAdapter.addAll(list);
            resume_nodata.setVisibility(View.GONE);
        }else {
            resume_nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        resume_recyclerview.refresh();
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof MineJobActivity){
            ((MineJobActivity) context).error(msg);
        }
    }

    @Override
    public void refresh() {
        jobResumeAdapter.clear();
    }

    @Override
    public void refreshComplete() {
        resume_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
