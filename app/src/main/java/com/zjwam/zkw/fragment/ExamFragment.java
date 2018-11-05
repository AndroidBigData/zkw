package com.zjwam.zkw.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.view.MainActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ExamAdapter;
import com.zjwam.zkw.customview.ExamDialog;
import com.zjwam.zkw.customview.ExamPopupWindow;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.ExamBean;
import com.zjwam.zkw.exam.ExamDetailsActivity;
import com.zjwam.zkw.mvp.presenter.ExamPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamPresenter;
import com.zjwam.zkw.mvp.view.IExamView;
import com.zjwam.zkw.util.KeyboardUtils;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment implements IExamView{
    private Context context;
    private ExamAdapter examAdapter;
    private LRecyclerView exam_recyclerview;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private EditText search_title_exam;
    private TextView search_fl;
    private int mCurrentCounter ,max_items,page;
    private boolean isRefresh;
    private IExamPresenter examPresenter;
    private String searchExam="",ids="";
    private ImageView exam_nodata;
    private RelativeLayout exam_relativeLayout;
    private int types;
    private ImageView hold;
    private int examItem;

    public ExamFragment() {
        // Required empty public constructor
    }

    public static ExamFragment newInstance(Context context) {
        ExamFragment fragment = new ExamFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        examPresenter = new ExamPresenter(context,this);
        examAdapter = new ExamAdapter(context);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(examAdapter);
        exam_recyclerview.setAdapter(lRecyclerViewAdapter);
        exam_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        exam_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        exam_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        exam_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        exam_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                mCurrentCounter = 0;
                searchExam = search_title_exam.getText().toString().trim();
                examPresenter.getExam(String.valueOf(page),searchExam,ids,isRefresh);
            }
        });
        exam_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter<max_items){
                    page++;
                    examPresenter.getExam(String.valueOf(page),searchExam,ids,isRefresh);
                }else {
                    exam_recyclerview.setNoMore(true);
                }
            }
        });
        exam_recyclerview.refresh();
        search_title_exam.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    searchExam = search_title_exam.getText().toString().trim();
                    if (searchExam.length()>0){
                        ids="";
                        KeyboardUtils.hideKeyboard(search_title_exam);
                        exam_recyclerview.refresh();
                    }else {
                        showMsg("输入内容不得为空！");
                    }
                }
                return false;
            }
        });
        search_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExamPopupWindow examPopupWindow = new ExamPopupWindow(context);
                examPopupWindow.showAsDropDown(exam_relativeLayout);
                examPopupWindow.setItemOnclick(new ExamPopupWindow.ItemOnclick() {
                    @Override
                    public void onClick(int type) {
                        types = type;
                        examPresenter.getExamTJ( String.valueOf(type));
                    }
                });
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (ZkwPreference.getInstance(getContext()).IsFlag()){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(examAdapter.getDataList().get(position).getId()));
                    bundle.putString("title",examAdapter.getDataList().get(position).getExam_name());
                    startActivity(new Intent(getActivity(), ExamDetailsActivity.class).putExtras(bundle));
                }else {
                    if (context instanceof MainActivity){
                        ((MainActivity) context).error("请先登录");
                    }
                }
            }
        });
        examAdapter.setHoldExam(new ExamAdapter.HoldExam() {
            @Override
            public void onClick(View view,int position) {
                if (ZkwPreference.getInstance(getContext()).IsFlag()){
                    hold = (ImageView) view;
                    examItem = position;
                    hold.setEnabled(false);
                    examPresenter.holdExam(String.valueOf(examAdapter.getDataList().get(examItem).getId()));
                }else {
                    if (context instanceof MainActivity){
                        ((MainActivity) context).error("请先登录");
                    }
                }
            }
        });
    }

    private void initView() {
        exam_recyclerview = getActivity().findViewById(R.id.exam_recyclerview);
        search_title_exam = getActivity().findViewById(R.id.search_title_exam);
        search_fl = getActivity().findViewById(R.id.search_fl);
        exam_nodata = getActivity().findViewById(R.id.exam_nodata);
        exam_relativeLayout = getActivity().findViewById(R.id.exam_relativeLayout);
    }

    @Override
    public void addExamItem(List<ExamBean.Exam> exam, int count) {
        max_items = count;
        if (max_items>0){
            examAdapter.addAll(exam);
            mCurrentCounter += exam.size();
            exam_nodata.setVisibility(View.GONE);
        }else {
            exam_nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof MainActivity){
            ((MainActivity) context).error(msg);
        }
    }

    @Override
    public void refresh() {
        examAdapter.clear();
    }

    @Override
    public void refreshComplele() {
        exam_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreError() {
        exam_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
            @Override
            public void reload() {
                examPresenter.getExam(String.valueOf(page),searchExam,ids,isRefresh);
            }
        });
    }

    @Override
    public void getExamFL(ClassSearchBean classSearchBean) {
        final ExamDialog examDialog = new ExamDialog(context,types,classSearchBean);
        examDialog.show();
        examDialog.setOnClickListerer(new ExamDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(String id,String title) {
                ids = id;
                search_title_exam.setText(title);
                exam_recyclerview.refresh();
                examDialog.dismiss();
            }
        });
    }

    @Override
    public void holdExam() {
        if (examAdapter.getDataList().get(examItem).getHold() == 0){
            hold.setImageResource(R.drawable.exam_hold);
            examAdapter.getDataList().get(examItem).setHold(1);
        }else {
            hold.setImageResource(R.drawable.exam_unhold);
            examAdapter.getDataList().get(examItem).setHold(0);
        }
    }

    @Override
    public void holdFinish() {
        hold.setEnabled(true);
    }
}
