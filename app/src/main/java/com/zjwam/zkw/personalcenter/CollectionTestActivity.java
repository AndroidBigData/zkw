package com.zjwam.zkw.personalcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ExamTestCollectionAdapter;
import com.zjwam.zkw.entity.ExamTestCollectionBean;
import com.zjwam.zkw.mvp.presenter.ExamTestCollectionPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamTestCollectionPresenter;
import com.zjwam.zkw.mvp.view.IExamTestCollectionView;

import java.util.List;

public class CollectionTestActivity extends BaseActivity implements IExamTestCollectionView{

    private ImageView back,collection_test_nodata;
    private TextView title;
    private LRecyclerView collection_test_recyclerview;
    private ExamTestCollectionAdapter examTestCollectionAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int mCurrentCounter ,max_items,page;
    private boolean isRefresh;
    private IExamTestCollectionPresenter testCollectionPresenter;
    private RelativeLayout view_time,view_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_test);
        initView();
        initData();
    }

    private void initData() {
        testCollectionPresenter = new ExamTestCollectionPresenter(this,this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("收藏试题");
        examTestCollectionAdapter = new ExamTestCollectionAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(examTestCollectionAdapter);
        collection_test_recyclerview.setAdapter(lRecyclerViewAdapter);
        collection_test_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        collection_test_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        collection_test_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        collection_test_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        collection_test_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                mCurrentCounter = 0;
                isRefresh = true;
                testCollectionPresenter.getTestCollection(isRefresh, String.valueOf(page));
            }
        });
        collection_test_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter<max_items){
                    page++;
                    testCollectionPresenter.getTestCollection(isRefresh, String.valueOf(page));
                }else {
                    collection_test_recyclerview.setNoMore(true);
                }
            }
        });
        collection_test_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view_time = view.findViewById(R.id.text_collection_relativeLayout);
                view_answer = view.findViewById(R.id.text_collection_answer);
                if (examTestCollectionAdapter.getDataList().get(position).isOpen()){
                    view_answer.setVisibility(View.VISIBLE);
                    view_time.setVisibility(View.GONE);
                    examTestCollectionAdapter.getDataList().get(position).setOpen(false);
                }else {
                    view_answer.setVisibility(View.GONE);
                    view_time.setVisibility(View.VISIBLE);
                    examTestCollectionAdapter.getDataList().get(position).setOpen(true);
                }
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        collection_test_nodata = findViewById(R.id.collection_test_nodata);
        title = findViewById(R.id.title);
        collection_test_recyclerview = findViewById(R.id.collection_test_recyclerview);
    }

    @Override
    public void onSuccess(List<ExamTestCollectionBean.Paper> list, int count) {
        max_items = count;
        if (max_items>0){
            examTestCollectionAdapter.addAll(list);
            mCurrentCounter += list.size();
            collection_test_nodata.setVisibility(View.GONE);
        }else {
            collection_test_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        examTestCollectionAdapter.clear();
    }

    @Override
    public void freshComplete() {
        collection_test_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
