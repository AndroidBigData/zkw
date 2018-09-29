package com.zjwam.zkw.fragment.news;


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
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.NewsAdapter;
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.mvp.presenter.MainNewsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMainNewsPresenter;
import com.zjwam.zkw.mvp.view.IMainNewsView;
import com.zjwam.zkw.news.NewsActivity;
import com.zjwam.zkw.news.NewsMoreActivity;
import com.zjwam.zkw.news.NewsWebActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainNewsFragment extends Fragment implements IMainNewsView{
    private Context context;
    private LRecyclerView main_news_n,main_news_w;
    private TextView news_n_more,news_w_more;
    private NewsAdapter newsAdapter_n,newsAdapter_w;
    private LRecyclerViewAdapter lRecyclerViewAdapter_n,lRecyclerViewAdapter_w;
    private ImageView news_n_nodata,news_w_nodata;
    private IMainNewsPresenter mainNewsPresenter;

    public MainNewsFragment() {
        // Required empty public constructor
    }

    public static MainNewsFragment newInstance(Context context) {
        MainNewsFragment fragment = new MainNewsFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mainNewsPresenter = new MainNewsPresenter(context,this);
        newsAdapter_n = new NewsAdapter(context);
        newsAdapter_w = new NewsAdapter(context);
        lRecyclerViewAdapter_n = new LRecyclerViewAdapter(newsAdapter_n);
        lRecyclerViewAdapter_w = new LRecyclerViewAdapter(newsAdapter_w);
        main_news_n.setAdapter(lRecyclerViewAdapter_n);
        main_news_w.setAdapter(lRecyclerViewAdapter_w);
        main_news_n.setLayoutManager(new LinearLayoutManager(getActivity()));
        main_news_w.setLayoutManager(new LinearLayoutManager(getActivity()));
        main_news_n.setPullRefreshEnabled(false);
        main_news_n.setLoadMoreEnabled(false);
        main_news_w.setPullRefreshEnabled(false);
        main_news_w.setLoadMoreEnabled(false);
        mainNewsPresenter.getMainNews();
        news_n_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id","3");
                bundle.putString("title","国内教育动态");
                startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
            }
        });
        news_w_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id","4");
                bundle.putString("title","国外教育动态");
                startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
            }
        });
        lRecyclerViewAdapter_n.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",newsAdapter_n.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });
        lRecyclerViewAdapter_w.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",newsAdapter_w.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });
    }

    private void initView() {
        main_news_n = getActivity().findViewById(R.id.main_news_n);
        main_news_w = getActivity().findViewById(R.id.main_news_w);
        news_n_more = getActivity().findViewById(R.id.news_n_more);
        news_w_more = getActivity().findViewById(R.id.news_w_more);
        news_n_nodata = getActivity().findViewById(R.id.news_n_nodata);
        news_w_nodata = getActivity().findViewById(R.id.news_w_nodata);
    }

    @Override
    public void setNews(List<NewsBean> news_n, List<NewsBean> news_w) {
        if (news_n.size()>0){
            newsAdapter_n.addAll(news_n);
            news_n_nodata.setVisibility(View.GONE);
        }else {
            news_n_nodata.setVisibility(View.VISIBLE);
        }
        if (news_w.size()>0){
            newsAdapter_w.addAll(news_w);
            news_w_nodata.setVisibility(View.GONE);
        }else {
            news_w_nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof NewsActivity){
            ((NewsActivity) context).error(msg);
        }
    }
}
