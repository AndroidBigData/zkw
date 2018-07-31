package com.zjwam.zkw.fragment.personalcenter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.MineClassAdapter;
import com.zjwam.zkw.entity.MineClassBean;
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
    private Context context;
    private PersonalCenterHttp personalCenterHttp;

    public LearnOverFragment() {
        // Required empty public constructor
    }

    public static LearnOverFragment newInstance(Context context) {
        LearnOverFragment fragment = new LearnOverFragment();
        fragment.context = context;
        return fragment;
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
        personalCenterHttp = new PersonalCenterHttp(context);
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
                personalCenterHttp.getLearnOver(uid, "2", String.valueOf(page));
                mineClassAdapter.clear();
            }
        });
        learnover_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.getLearnOver(uid, "2", String.valueOf(page));
                } else {
                    learnover_recyclerview.setNoMore(true);
                }
            }
        });
        learnover_recyclerview.refresh();
    }

    private void addItems(List<MineClassBean.itemBean> list) {
        mineClassAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    public void getLearnOver(Response<MineClassBean> response) {
        MineClassBean mineClassBean = response.body();
        max_items = mineClassBean.getData().getCount();
        if (mineClassBean.getData().getClass_list().size() > 0) {
            addItems(mineClassBean.getData().getClass_list());
            learnover_nodata.setVisibility(View.GONE);
        } else {
            learnover_nodata.setVisibility(View.VISIBLE);
        }
    }

    public void getLearnOverError(Response<MineClassBean> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    public void getLearnOverFinish() {
        learnover_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
