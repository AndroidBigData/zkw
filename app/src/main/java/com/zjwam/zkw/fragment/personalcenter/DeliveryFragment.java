package com.zjwam.zkw.fragment.personalcenter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.MineJobDeliveryAdapter;
import com.zjwam.zkw.entity.MineJobDeliveryBean;
import com.zjwam.zkw.mvp.presenter.MineJobDeliveryPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMineJobDeliveryPresenter;
import com.zjwam.zkw.mvp.view.IMineJobDeliveryView;
import com.zjwam.zkw.personalcenter.job.MineDeliveryActivity;
import com.zjwam.zkw.personalcenter.job.MineJobActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment implements IMineJobDeliveryView{
    private Context context;
    private LRecyclerView delivery_recyclerview;
    private MineJobDeliveryAdapter jobDeliveryAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ImageView delivery_nodata;
    private int mCurrentCounter ,max_items,page;
    private boolean isRefresh;
    private IMineJobDeliveryPresenter jobDeliveryPresenter;

    public DeliveryFragment() {
        // Required empty public constructor
    }

    public static DeliveryFragment newInstance(Context context) {

        DeliveryFragment fragment = new DeliveryFragment();
        fragment.context = context;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        jobDeliveryPresenter = new MineJobDeliveryPresenter(context,this);
        jobDeliveryAdapter = new MineJobDeliveryAdapter(context);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobDeliveryAdapter);
        delivery_recyclerview.setAdapter(lRecyclerViewAdapter);
        delivery_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        delivery_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        delivery_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        delivery_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        delivery_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                jobDeliveryPresenter.getJobDelivery(String.valueOf(page),isRefresh);
            }
        });
        delivery_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter<max_items){
                    page++;
                    jobDeliveryPresenter.getJobDelivery(String.valueOf(page),isRefresh);
                }else {
                    delivery_recyclerview.setNoMore(true);
                }
            }
        });
        delivery_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(jobDeliveryAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getActivity(), MineDeliveryActivity.class).putExtras(bundle));
            }
        });
    }

    private void initView() {
        delivery_recyclerview = getActivity().findViewById(R.id.delivery_recyclerview);
        delivery_nodata = getActivity().findViewById(R.id.delivery_nodata);
    }

    @Override
    public void addRecordItem(List<MineJobDeliveryBean.Resume> list, int count) {
        max_items = count;
        if (max_items>0){
            jobDeliveryAdapter.addAll(list);
            mCurrentCounter += list.size();
            delivery_nodata.setVisibility(View.GONE);
        }else {
            delivery_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof MineJobActivity){
            ((MineJobActivity) context).error(msg);
        }
    }

    @Override
    public void refresh() {
        jobDeliveryAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        delivery_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
