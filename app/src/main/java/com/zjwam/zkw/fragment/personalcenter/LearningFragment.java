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
public class LearningFragment extends Fragment {
    private LRecyclerView learning_recyclerview;
    private MineClassAdapter mineClassAdapter;
    private int mCurrentCounter = 0;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1;
    private int max_items;
    private ImageView learning_nodata;
    private String uid;
    private Context context;
    private PersonalCenterHttp personalCenterHttp;
    public LearningFragment() {
        // Required empty public constructor
    }
    public static LearningFragment newInstance(Context context) {
        LearningFragment learningFragment = new LearningFragment();
        learningFragment.context = context;
        return learningFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        uid = ZkwPreference.getInstance(getActivity()).getUid();
        learning_recyclerview = getActivity().findViewById(R.id.learning_recyclerview);
        learning_nodata = getActivity().findViewById(R.id.learning_nodata);

        personalCenterHttp = new PersonalCenterHttp(context);

        mineClassAdapter = new MineClassAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mineClassAdapter);
        learning_recyclerview.setAdapter(lRecyclerViewAdapter);
        learning_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        learning_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        learning_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        learning_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        learning_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                personalCenterHttp.getLearning(uid,"1", String.valueOf(page));
                mineClassAdapter.clear();
            }
        });
        learning_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.getLearning(uid,"1", String.valueOf(page));
                }else {
                    learning_recyclerview.setNoMore(true);
                }
            }
        });
        learning_recyclerview.refresh();
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

    public void getLearning(Response<MineClassBean> response) {
        MineClassBean mineClassBean = response.body();
        if (mineClassBean.getData().getClass_list().size()>0){
            addItems(mineClassBean.getData().getClass_list());
            learning_nodata.setVisibility(View.GONE);
        }else {
            learning_nodata.setVisibility(View.VISIBLE);
        }
        max_items = mineClassBean.getData().getCount();
    }

    public void getLearningError(Response<MineClassBean> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
    }

    public void getLearningFinish() {
        learning_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

}
