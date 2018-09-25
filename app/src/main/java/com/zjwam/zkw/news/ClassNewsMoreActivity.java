package com.zjwam.zkw.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.SearchListAdapter;
import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.mvp.presenter.ClassNewsMorePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IClassNewsMorePresenter;
import com.zjwam.zkw.mvp.view.IClassNewsMoreView;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;

public class ClassNewsMoreActivity extends BaseActivity implements IClassNewsMoreView {

    private ImageView back,class_news_more_nodata;
    private TextView title;
    private String id,titleName;
    private LRecyclerView class_news_more_recyclerview;
    private SearchListAdapter searchListAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1, mCurrentCounter, max_items;
    private boolean isRefresh;
    private IClassNewsMorePresenter newsMorePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_news_more);
        initView();
        initData();
    }

    private void initData() {
        id = getIntent().getExtras().getString("id");
        titleName = getIntent().getExtras().getString("title");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText(titleName);
        newsMorePresenter = new ClassNewsMorePresenter(this,this);
        searchListAdapter = new SearchListAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(searchListAdapter);
        class_news_more_recyclerview.setAdapter(lRecyclerViewAdapter);
        class_news_more_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        class_news_more_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        class_news_more_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        class_news_more_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        class_news_more_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                newsMorePresenter.getNews(id, String.valueOf(page),isRefresh);
            }
        });
        class_news_more_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    newsMorePresenter.getNews(id, String.valueOf(page),isRefresh);
                } else {
                    class_news_more_recyclerview.setNoMore(true);
                }
            }
        });
        class_news_more_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(searchListAdapter.getDataList().get(position).getId()));
                bundle.putString("title", searchListAdapter.getDataList().get(position).getName());
                Intent intent = new Intent(getBaseContext(), Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        class_news_more_recyclerview = findViewById(R.id.class_news_more_recyclerview);
        class_news_more_nodata = findViewById(R.id.class_news_more_nodata);
    }

    @Override
    public void getNews(List<ClassInfo> data, int count) {
        max_items = count;
        if (max_items>0){
            searchListAdapter.addAll(data);
            mCurrentCounter +=data.size();
            class_news_more_nodata.setVisibility(View.GONE);
        }else {
            class_news_more_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        searchListAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        class_news_more_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
