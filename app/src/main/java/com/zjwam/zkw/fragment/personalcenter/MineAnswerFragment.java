package com.zjwam.zkw.fragment.personalcenter;


import android.content.Context;
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
import com.zjwam.zkw.HttpUtils.HttpErrorMsg;
import com.zjwam.zkw.HttpUtils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalCourseAnswerAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PersonalMineAnswerBean;
import com.zjwam.zkw.entity.PersonalMineAskBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.personalcenter.CourseAnswerActivity;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineAnswerFragment extends Fragment {

    private LRecyclerView mine_answer_recyclerview;
    private PersonalCourseAnswerAdapter courseAnswerAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1, mCurrentCounter = 0, max_items;
    private String uid;
    private boolean isRefresh = true;
    private ImageView mine_answer_nodata;
    private Context context;
    private Throwable exception;
    private PersonalCenterHttp personalCenterHttp;


    public MineAnswerFragment() {
        // Required empty public constructor
    }

    public static MineAnswerFragment newInstance(Context context) {
        MineAnswerFragment fragment = new MineAnswerFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getActivity()).getUid();
        personalCenterHttp = new PersonalCenterHttp(context);
        courseAnswerAdapter = new PersonalCourseAnswerAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(courseAnswerAdapter);
        mine_answer_recyclerview.setAdapter(lRecyclerViewAdapter);
        mine_answer_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mine_answer_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mine_answer_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mine_answer_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mine_answer_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                personalCenterHttp.mineAnswerQuestion(uid, String.valueOf(page));
            }
        });
        mine_answer_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.mineAnswerQuestion(uid, String.valueOf(page));
                } else {
                    mine_answer_recyclerview.setNoMore(true);
                }
            }
        });
        mine_answer_recyclerview.refresh();
    }

    private void addItems(List<PersonalMineAnswerBean.getAnswerItems> answer) {
        courseAnswerAdapter.addAll(answer);
        mCurrentCounter += answer.size();
    }


    private void initView() {
        mine_answer_recyclerview = getActivity().findViewById(R.id.mine_answer_recyclerview);
        mine_answer_nodata = getActivity().findViewById(R.id.mine_answer_nodata);
    }

    public void mineAnswerQuestion(Response<ResponseBean<PersonalMineAnswerBean>> response) {
        if (isRefresh) courseAnswerAdapter.clear();
        ResponseBean<PersonalMineAnswerBean> data = response.body();
        max_items = data.data.getCount();
        if (data.data.getAnswer().size() > 0) {
            addItems(data.data.getAnswer());
            mine_answer_nodata.setVisibility(View.GONE);
        } else mine_answer_nodata.setVisibility(View.VISIBLE);
    }

    public void mineAnswerQuestionError(Response<ResponseBean<PersonalMineAnswerBean>> response) {
        exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()){
            ((CourseAnswerActivity) context).error(error);
        }
    }

    public void mineAnswerQuestionFinesh() {
        mine_answer_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        if (!(exception instanceof MyException)) {
            mine_answer_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    personalCenterHttp.mineAnswerQuestion(uid, String.valueOf(page));
                }
            });
        }
    }
}
