package com.zjwam.zkw.fragment.personalcenter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.MineClassAdapter;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.MineClassBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnOverFragment extends Fragment {
    private LRecyclerView learnover_recyclerview;
    private ImageView learnover_nodata;
    private MineClassAdapter mineClassAdapter;
    private int mCurrentCounter = 0;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1;
    private int max_items;
    private String uid;

    public LearnOverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_over, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        uid = ZkwPreference.getInstance(getActivity()).getUid();
        learnover_recyclerview = getActivity().findViewById(R.id.learnover_recyclerview);
        learnover_nodata = getActivity().findViewById(R.id.learnover_nodata);

        mineClassAdapter = new MineClassAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mineClassAdapter);
        learnover_recyclerview.setAdapter(lRecyclerViewAdapter);
        learnover_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        learnover_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        learnover_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        learnover_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        learnover_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                getData(page);
                mineClassAdapter.clear();
            }
        });
        learnover_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    getData(page);
                } else {
                    learnover_recyclerview.setNoMore(true);
                }
            }
        });
        learnover_recyclerview.refresh();
    }

    private void getData(int page) {
        OkGo.<MineClassBean>post(Config.URL + "api/user/all_class")
                .params("uid", uid)
                .params("type", 2)
                .params("page", page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new Json2Callback<MineClassBean>() {
                    @Override
                    public void onSuccess(Response<MineClassBean> response) {
                        MineClassBean mineClassBean = response.body();
                        max_items = mineClassBean.getData().getCount();
                        if (mineClassBean.getData().getClass_list().size()>0){
                            addItems(mineClassBean.getData().getClass_list());
                            learnover_nodata.setVisibility(View.GONE);
                        }else {
                            learnover_nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        learnover_recyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void addItems(List<MineClassBean.itemBean> list) {
        mineClassAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
