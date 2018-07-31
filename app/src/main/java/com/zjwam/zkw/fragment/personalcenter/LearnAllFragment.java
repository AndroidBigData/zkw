package com.zjwam.zkw.fragment.personalcenter;


import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.MineClassAdapter;
import com.zjwam.zkw.entity.MineClassBean;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnAllFragment extends Fragment {
    private LRecyclerView learn_all_lrecyclerview;
    private MineClassAdapter mineClassAdapter;
    private int mCurrentCounter = 0;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1;
    private int max_items;
    private ImageView learn_all_nodata;
    private String uid;
    private Context context;
    private PersonalCenterHttp personalCenterHttp;

    public LearnAllFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public LearnAllFragment(Context context) {
        this.context = context;
    }

    public static LearnAllFragment newInstance(Context context) {
        LearnAllFragment fragment = new LearnAllFragment();
        fragment.context = context;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        uid = ZkwPreference.getInstance(getActivity()).getUid();
        learn_all_lrecyclerview = getActivity().findViewById(R.id.learn_all_lrecyclerview);
        learn_all_nodata = getActivity().findViewById(R.id.learn_all_nodata);
        personalCenterHttp = new PersonalCenterHttp(context);
        mineClassAdapter = new MineClassAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mineClassAdapter);
        learn_all_lrecyclerview.setAdapter(lRecyclerViewAdapter);
        learn_all_lrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        learn_all_lrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置底部加载颜色
        learn_all_lrecyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        //设置底部加载文字提示
        learn_all_lrecyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");

        learn_all_lrecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                personalCenterHttp.getLearnAll(uid,"0", String.valueOf(page));
                mineClassAdapter.clear();
            }
        });
        learn_all_lrecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.getLearnAll(uid,"0", String.valueOf(page));
                }else {
                    learn_all_lrecyclerview.setNoMore(true);
                }
            }
        });
        learn_all_lrecyclerview.refresh();
        mineClassAdapter.setOnClick(new MineClassAdapter.learnOnClick() {
            @Override
            public void onLearnClick(int classid,String title) {
//                Toast.makeText(getActivity(),""+classid,Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("id",String.valueOf(classid));
                bundle.putString("title",title);
                Intent intent = new Intent(getActivity(), Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void addItems(List<MineClassBean.itemBean> list) {
        mineClassAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    public void getLearnAll(Response<MineClassBean> response) {
        MineClassBean mineClassBean = response.body();
        if (mineClassBean.getData().getClass_list().size()>0){
            addItems(mineClassBean.getData().getClass_list());
            learn_all_nodata.setVisibility(View.GONE);
        }else {
            learn_all_nodata.setVisibility(View.VISIBLE);
        }

        max_items = mineClassBean.getData().getCount();
    }

    public void getLearnAllError(Response<MineClassBean> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
    }

    public void getLearnAllFinish() {
        learn_all_lrecyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
