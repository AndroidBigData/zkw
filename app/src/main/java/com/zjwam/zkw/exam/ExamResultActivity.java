package com.zjwam.zkw.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jaeger.library.StatusBarUtil;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ExamResultAdapter;
import com.zjwam.zkw.entity.ExamBaseResultBean;
import com.zjwam.zkw.mvp.presenter.ExamResultPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamResultPresenter;
import com.zjwam.zkw.mvp.view.IExamResultView;

public class ExamResultActivity extends BaseActivity implements IExamResultView{

    private ImageView exam_result_back;
    private TextView exam_result_do,exam_result_rate,exam_result_times,exam_result_deails;
    private LRecyclerView exam_result_recyclerview;
    private String eid,id,title;
    private ExamResultAdapter examResultAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private IExamResultPresenter examResultPresenter;
    private boolean isRefresh;
    private ImageView hold;
    private int itemPositon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        setStatusBar();
        eid = getIntent().getExtras().getString("eid");
        id = getIntent().getExtras().getString("id");
        title = getIntent().getExtras().getString("title");
        initView();
        initData();
    }

    private void initData() {
        exam_result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        exam_result_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("title",title);
                startActivity(new Intent(getBaseContext(), ExamDetailsActivity.class).putExtras(bundle));
                finish();
            }
        });
        examResultPresenter = new ExamResultPresenter(this,this);
        examResultAdapter = new ExamResultAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(examResultAdapter);
        exam_result_recyclerview.setAdapter(lRecyclerViewAdapter);
        exam_result_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        exam_result_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        exam_result_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        exam_result_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        exam_result_recyclerview.setLoadMoreEnabled(false);
        exam_result_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                examResultPresenter.getExamResult(id,eid,isRefresh);
            }
        });
        exam_result_recyclerview.refresh();
        examResultAdapter.setHoldExamTest(new ExamResultAdapter.HoldExamTest() {
            @Override
            public void setOnClick(View view, int positon) {
                itemPositon = positon;
                hold = (ImageView) view;
                hold.setEnabled(false);
                examResultPresenter.holdExamTest(String.valueOf(examResultAdapter.getDataList().get(itemPositon).getId()),
                        String.valueOf(examResultAdapter.getDataList().get(itemPositon).getEid()));
            }
        });
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent),60);
    }

    private void initView() {
        exam_result_back = findViewById(R.id.exam_result_back);
        exam_result_do = findViewById(R.id.exam_result_do);
        exam_result_rate = findViewById(R.id.exam_result_rate);
        exam_result_times = findViewById(R.id.exam_result_times);
        exam_result_deails = findViewById(R.id.exam_result_deails);
        exam_result_recyclerview = findViewById(R.id.exam_result_recyclerview);
    }

    @Override
    public void onSuccess(ExamBaseResultBean data) {
        examResultAdapter.addAll(data.getQuestion());
        exam_result_rate.setText("正确率："+data.getRate()+"%");
        exam_result_times.setText("总用时："+data.getTime());
        exam_result_deails.setText("正确："+data.getRnum()+"   "+"错误："+data.getWnum());
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        examResultAdapter.clear();
    }

    @Override
    public void freshComplete() {
        exam_result_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void holdExamTest() {
        if (examResultAdapter.getDataList().get(itemPositon).getHold() == 1){
            hold.setImageResource(R.drawable.exam_unhold);
            examResultAdapter.getDataList().get(itemPositon).setHold(0);
        }else {
            hold.setImageResource(R.drawable.exam_hold);
            examResultAdapter.getDataList().get(itemPositon).setHold(1);
        }
    }

    @Override
    public void holdFinish() {
        hold.setEnabled(true);
    }
}
