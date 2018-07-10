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
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalCourseAskAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PersonalMineAskBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineAskFragment extends Fragment {

    private LRecyclerView mineask_recyclerview;
    private PersonalCourseAskAdapter courseAskAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter = 0,max_items;
    private String uid;
    private boolean isRefresh = true;
    private ImageView mineask_nodata;

    public MineAskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine_ask, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getActivity()).getUid();
        courseAskAdapter = new PersonalCourseAskAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(courseAskAdapter);
        mineask_recyclerview.setAdapter(lRecyclerViewAdapter);
        mineask_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineask_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mineask_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mineask_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mineask_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                getData(uid,page);
            }
        });
        mineask_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    getData(uid,page);
                } else {
                    mineask_recyclerview.setNoMore(true);
                }
            }
        });

        mineask_recyclerview.refresh();
    }

    private void getData(final String uid, final int page) {
        OkGo.<ResponseBean<PersonalMineAskBean>>post(Config.URL + "api/user/question")
                .params("uid",uid)
                .params("page",page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<PersonalMineAskBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PersonalMineAskBean>> response) {
                        if (isRefresh) courseAskAdapter.clear();
                        ResponseBean<PersonalMineAskBean> data = response.body();
                        max_items = data.data.getCount();
                        if (data.data.getQuestion().size()>0){
                            addItems(data.data.getQuestion());
                            mineask_nodata.setVisibility(View.GONE);
                        }else mineask_nodata.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onError(Response<ResponseBean<PersonalMineAskBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) Toast.makeText(getActivity(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mineask_recyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        if (!NetworkUtils.isNetAvailable(getActivity())) {
                            mineask_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getData(uid, page);
                                }
                            });
                        }
                    }
                });
    }

    private void addItems(List<PersonalMineAskBean.getAskItems> question) {
        courseAskAdapter.addAll(question);
        mCurrentCounter += question.size();
    }

    private void initView() {
        mineask_recyclerview = getActivity().findViewById(R.id.mineask_recyclerview);
        mineask_nodata = getActivity().findViewById(R.id.mineask_nodata);
    }
}
