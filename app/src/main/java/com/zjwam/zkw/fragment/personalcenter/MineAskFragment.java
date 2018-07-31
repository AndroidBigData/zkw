package com.zjwam.zkw.fragment.personalcenter;


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

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalCourseAskAdapter;
import com.zjwam.zkw.entity.PersonalMineAskBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.personalcenter.CourseAnswerActivity;
import com.zjwam.zkw.personalcenter.PersonalMineAskActivity;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineAskFragment extends Fragment {

    private LRecyclerView mineask_recyclerview;
    private PersonalCourseAskAdapter courseAskAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter = 0,max_items;
    private String uid;
    private boolean isRefresh = true;
    private ImageView mineask_nodata;
    private ResponseBean<PersonalMineAskBean> data;
    private Context context;
    private Throwable exception;
    private PersonalCenterHttp personalCenterHttp;

    public MineAskFragment() {
        // Required empty public constructor
    }

    public static MineAskFragment newInstance(Context context) {
        MineAskFragment fragment = new MineAskFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine_ask, container, false);
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
        courseAskAdapter = new PersonalCourseAskAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(courseAskAdapter);
        mineask_recyclerview.setAdapter(lRecyclerViewAdapter);
        mineask_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineask_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mineask_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mineask_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mineask_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                personalCenterHttp.mineAskQuestion(uid, String.valueOf(page));
            }
        });
        mineask_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.mineAskQuestion(uid, String.valueOf(page));
                } else {
                    mineask_recyclerview.setNoMore(true);
                }
            }
        });

        mineask_recyclerview.refresh();
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(data.data.getQuestion().get(position).getId()));
                startActivity(new Intent(getActivity(),PersonalMineAskActivity.class).putExtras(bundle));
            }
        });
    }

    private void addItems(List<PersonalMineAskBean.getAskItems> question) {
        courseAskAdapter.addAll(question);
        mCurrentCounter += question.size();
    }

    private void initView() {
        mineask_recyclerview = getActivity().findViewById(R.id.mineask_recyclerview);
        mineask_nodata = getActivity().findViewById(R.id.mineask_nodata);
    }

    public void mineAskQuestion(Response<ResponseBean<PersonalMineAskBean>> response) {
        if (isRefresh) courseAskAdapter.clear();
        data = response.body();
        max_items = data.data.getCount();
        if (data.data.getQuestion().size()>0){
            addItems(data.data.getQuestion());
            mineask_nodata.setVisibility(View.GONE);
        }else mineask_nodata.setVisibility(View.VISIBLE);
    }

    public void mineAskQuestionError(Response<ResponseBean<PersonalMineAskBean>> response) {
        exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()){
            ((CourseAnswerActivity) context).error(error);
        }
    }

    public void mineAskQuestionFinish() {
        mineask_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        if (!(exception instanceof MyException)) {
            mineask_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    personalCenterHttp.mineAskQuestion(uid, String.valueOf(page));
                }
            });
        }
    }
}
