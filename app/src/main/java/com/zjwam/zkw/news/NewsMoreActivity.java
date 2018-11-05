package com.zjwam.zkw.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.NewsAdapter;
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.mvp.presenter.NewsMorePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.INewsMorePresenter;
import com.zjwam.zkw.mvp.view.INewsMoreView;
import com.zjwam.zkw.webview.NewsWebActivity;

import java.util.List;

public class NewsMoreActivity extends BaseActivity implements INewsMoreView{
    private ImageView back,news_more_nodata;
    private TextView title;
    private LRecyclerView news_more_recyclerview;
    private NewsAdapter newsAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1, mCurrentCounter, max_items;
    private boolean isRefresh;
    private String id,titleName,city;
    private INewsMorePresenter newsMorePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_more);
        initView();
        initData();
    }

    private void initData() {
        id = getIntent().getExtras().getString("id");
        titleName = getIntent().getExtras().getString("title");
        city = getIntent().getExtras().getString("city");
        newsMorePresenter = new NewsMorePresenter(this,this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText(titleName);
        newsAdapter = new NewsAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(newsAdapter);
        news_more_recyclerview.setAdapter(lRecyclerViewAdapter);
        news_more_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        news_more_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        news_more_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        news_more_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        news_more_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                newsMorePresenter.getNews(id, String.valueOf(page),city,isRefresh);
            }
        });
        news_more_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter<max_items){
                    page++;
                    newsMorePresenter.getNews(id, String.valueOf(page),city,isRefresh);
                }else {
                    news_more_recyclerview.setNoMore(true);
                }
            }
        });
        news_more_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",newsAdapter.getDataList().get(position).getUrl());
                startActivity(new Intent(getBaseContext(),NewsWebActivity.class).putExtras(bundle));
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        news_more_recyclerview = findViewById(R.id.news_more_recyclerview);
        news_more_nodata = findViewById(R.id.news_more_nodata);
    }

    @Override
    public void getNews(List<NewsBean> data, int count) {
        max_items = count;
        if (max_items>0){
            newsAdapter.addAll(data);
            mCurrentCounter += data.size();
            news_more_nodata.setVisibility(View.GONE);
        }else {
            news_more_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        newsAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        news_more_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
