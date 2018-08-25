package com.zjwam.zkw.personalcenter;

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
import com.zjwam.zkw.adapter.ExamCollectionAdapter;
import com.zjwam.zkw.entity.CollectionExamBean;
import com.zjwam.zkw.exam.ExamDetailsActivity;
import com.zjwam.zkw.mvp.presenter.ExamCollectionPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamCollectionPresenter;
import com.zjwam.zkw.mvp.view.IExamCollectionView;

import java.util.List;

public class CollectionExamActivity extends BaseActivity implements IExamCollectionView{

    private ImageView back,collection_exam_nodata;
    private TextView title;
    private LRecyclerView collection_exam_recyclerview;
    private ExamCollectionAdapter examCollectionAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private boolean isRefresh;
    private int mCurrentCounter, max_items, page;
    private IExamCollectionPresenter examCollectionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_exam);
        initView();
        initData();
    }

    private void initData() {
        examCollectionPresenter = new ExamCollectionPresenter(this,this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("收藏试卷");
        examCollectionAdapter = new ExamCollectionAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(examCollectionAdapter);
        collection_exam_recyclerview.setAdapter(lRecyclerViewAdapter);
        collection_exam_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        collection_exam_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        collection_exam_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        collection_exam_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        collection_exam_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                examCollectionPresenter.getCollectionItem(isRefresh, String.valueOf(page));
            }
        });
        collection_exam_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter<max_items){
                    page++;
                    examCollectionPresenter.getCollectionItem(isRefresh, String.valueOf(page));
                }else {
                    collection_exam_recyclerview.setNoMore(true);
                }
            }
        });
        collection_exam_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(examCollectionAdapter.getDataList().get(position).getEid()));
                bundle.putString("title",examCollectionAdapter.getDataList().get(position).getName());
                startActivity(new Intent(getBaseContext(), ExamDetailsActivity.class).putExtras(bundle));
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        collection_exam_recyclerview = findViewById(R.id.collection_exam_recyclerview);
        collection_exam_nodata = findViewById(R.id.collection_exam_nodata);
    }

    @Override
    public void onSuccess(List<CollectionExamBean.Hold> list,int count) {
        max_items = count;
        if (max_items>0) {
            examCollectionAdapter.addAll(list);
            mCurrentCounter += list.size();
            collection_exam_nodata.setVisibility(View.GONE);
        }else {
            collection_exam_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        examCollectionAdapter.clear();
    }

    @Override
    public void freshComplete() {
        collection_exam_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
