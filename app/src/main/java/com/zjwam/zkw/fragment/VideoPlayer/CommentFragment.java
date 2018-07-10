package com.zjwam.zkw.fragment.VideoPlayer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.zjwam.zkw.adapter.VideoCommentAdapter;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.CommentBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {

    private TextView comment_num;
    private RatingBar comment_rating;
    private LRecyclerView comment_list;
    private String id = "";
    private int page = 1;
    private int max_items;
    private int mCurrentCounter = 0;
    private VideoCommentAdapter videoCommentAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<CommentBean.Comment> commentBeans;
    private boolean isLoadMore = false;
    private ImageView comment_nodata;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) id = getArguments().getString("id");
        initView();
        initData();
    }

    private void initData() {
        videoCommentAdapter = new VideoCommentAdapter(getActivity());

        lRecyclerViewAdapter = new LRecyclerViewAdapter(videoCommentAdapter);
        comment_list.setAdapter(lRecyclerViewAdapter);
        comment_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        comment_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        comment_list.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        comment_list.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        comment_list.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                videoCommentAdapter.clear();
                page = 1;
                mCurrentCounter = 0;
                getData(page,id);
            }
        });
        comment_list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                if (mCurrentCounter < max_items) {
                    page++;
                    getData(page,id);
                } else {
                    comment_list.setNoMore(true);
                }
            }
        });

        comment_list.refresh();
    }

    private void addItems(List<CommentBean.Comment> list) {

        videoCommentAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    private void getData(final int page, final String id) {
        OkGo.<CommentBean>get(Config.URL + "api/play/comment?id="+ id + "&page=" + page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new Json2Callback<CommentBean>() {
                    @Override
                    public void onSuccess(Response<CommentBean> response) {
                        CommentBean commentBean = response.body();
                        if (!isLoadMore) {
                            comment_num.setText("综合评分：" + commentBean.getStar());
                            comment_rating.setNumStars(commentBean.getStar());
                        }
                        max_items = commentBean.getCount();
                        commentBeans = commentBean.getComment();
                        if (max_items>0){
                            comment_nodata.setVisibility(View.GONE);
                            addItems(commentBeans);
                        }else {
                            comment_nodata.setVisibility(View.VISIBLE);
                        }
                        isLoadMore = false;
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        comment_list.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        if (!NetworkUtils.isNetAvailable(getActivity())){
                            comment_list.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getData(page,id);
                                }
                            });
                        }
                    }
                });
    }

    private void initView() {
        comment_num = getActivity().findViewById(R.id.comment_num);
        comment_rating = getActivity().findViewById(R.id.comment_rating);
        comment_list = getActivity().findViewById(R.id.comment_list);
        comment_nodata = getActivity().findViewById(R.id.comment_nodata);
    }
}
