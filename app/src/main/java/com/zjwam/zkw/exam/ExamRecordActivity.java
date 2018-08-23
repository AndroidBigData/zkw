package com.zjwam.zkw.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ExamRecordAdapter;
import com.zjwam.zkw.customview.BasicDialog;
import com.zjwam.zkw.entity.ExamRecordBean;
import com.zjwam.zkw.mvp.presenter.ExamRecordPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamRecordPresenter;
import com.zjwam.zkw.mvp.view.IExamRecordView;

import java.util.List;

public class ExamRecordActivity extends BaseActivity implements IExamRecordView {

    private ImageView back, exam_record_nodata;
    private TextView title;
    private LRecyclerView exam_record_recyclerview;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private boolean isRefresh;
    private int mCurrentCounter, max_items, page;
    private ExamRecordAdapter examRecordAdapter;
    private IExamRecordPresenter examRecordPresenter;
    private View koo;
    private BasicDialog basicDialog;
    private int deletePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_record);
        initView();
        initData();
    }

    private void initData() {
        examRecordPresenter = new ExamRecordPresenter(this, this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("做题记录");
        examRecordAdapter = new ExamRecordAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(examRecordAdapter);
        exam_record_recyclerview.setAdapter(lRecyclerViewAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        exam_record_recyclerview.setLayoutManager(linearLayoutManager);
        exam_record_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        exam_record_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        exam_record_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        exam_record_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                examRecordPresenter.getRecodItem(String.valueOf(page), isRefresh);
            }
        });
        exam_record_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    examRecordPresenter.getRecodItem(String.valueOf(page), isRefresh);
                } else {
                    exam_record_recyclerview.setNoMore(true);
                }
            }
        });
        exam_record_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                koo = view.findViewById(R.id.exam_record_item_add);
                if (examRecordAdapter.getDataList().get(position).isOpen()){
                    koo.setVisibility(View.GONE);
                    examRecordAdapter.getDataList().get(position).setOpen(false);
                }else {
                    koo.setVisibility(View.VISIBLE);
                    examRecordAdapter.getDataList().get(position).setOpen(true);
                }
            }
        });
        examRecordAdapter.setOpenItem(new ExamRecordAdapter.OpenedItem() {
            @Override
            public void openItem(View view,ExamRecordBean.Detial options) {
                error(""+options.getId());
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(options.getId()));
                bundle.putString("eid", String.valueOf(options.getEid()));
                startActivity(new Intent(getBaseContext(),ExamResultActivity.class).putExtras(bundle));
            }
        });

        lRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                basicDialog = new BasicDialog(ExamRecordActivity.this,"确定删除做题记录吗?");
                basicDialog.show();
                basicDialog.setDialog(new BasicDialog.BasicDialogListener() {
                    @Override
                    public void confirm() {
                        deletePosition = position;
                        examRecordPresenter.deleteRecord(String.valueOf(examRecordAdapter.getDataList().get(deletePosition).getId()));
                    }

                    @Override
                    public void cancel() {
                        basicDialog.dismiss();
                    }
                });

            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        exam_record_recyclerview = findViewById(R.id.exam_record_recyclerview);
        exam_record_nodata = findViewById(R.id.exam_record_nodata);
    }

    @Override
    public void addRecordItem(List<ExamRecordBean.Exam> exam, int count) {
        max_items = count;
        if (count > 0) {
            examRecordAdapter.addAll(exam);
            mCurrentCounter += exam.size();
            exam_record_nodata.setVisibility(View.GONE);
        } else {
            exam_record_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void refresh() {
        examRecordAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        exam_record_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteRecord() {
        examRecordAdapter.remove(deletePosition);
        basicDialog.dismiss();
    }
}
