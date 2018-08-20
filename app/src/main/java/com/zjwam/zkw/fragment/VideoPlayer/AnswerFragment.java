package com.zjwam.zkw.fragment.VideoPlayer;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.VideoPlayerHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.VideoAskAnswerAdapter;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VideoAskAnswerBean;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.VideoAnswerActivity;
import com.zjwam.zkw.videoplayer.more.CommmentActivity;
import com.zjwam.zkw.videoplayer.more.QuestionActivity;
import com.zjwam.zkw.videoplayer.more.WriteNoteActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends Fragment {

    private FloatingActionButton fab;
    private List<Map<String, Object>> data;
    private Map<String, Object> item;
    private GridView video_more_gridview;
    private RelativeLayout more_msg;
    private boolean isOpen = false;
    private String id = "", vid = "";
    private Bundle bundle;
    private Context context;
    private LRecyclerView video_answer_recyclerview;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private VideoAskAnswerAdapter videoAskAnswerAdapter;
    private int page = 1, mCurrentCounter = 0, max_items;
    private VideoPlayerHttp videoPlayerHttp;
    private boolean isNetVork = false, isRefresh = false;
    private ImageView video_answer_nodata;
    private List<VideoAskAnswerBean.getVideoAnswerItems> items;

    public AnswerFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AnswerFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            id = getArguments().getString("id");
        }
        initView();
        initData();
    }

    private void initData() {
        createData();
        String[] from = {"img", "title"};
        int[] to = {R.id.more_img, R.id.more_title};
        SimpleAdapter personalAdapter = new SimpleAdapter(getActivity(), data, R.layout.more_fab_layout, from, to);
        video_more_gridview.setAdapter(personalAdapter);
        bundle = new Bundle();
        bundle.putString("id", id);
        videoAskAnswerAdapter = new VideoAskAnswerAdapter(context);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(videoAskAnswerAdapter);
        video_answer_recyclerview.setAdapter(lRecyclerViewAdapter);
        video_answer_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        video_answer_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        video_answer_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        video_answer_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        videoPlayerHttp = new VideoPlayerHttp(context);
//        videoPlayerHttp.getVideoAnswer(id, page);
        video_answer_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mCurrentCounter = 0;
                page = 1;
                videoPlayerHttp.getVideoAnswer(vid, page);
            }
        });
        video_answer_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isNetVork = NetworkUtils.isNetAvailable(context);
                if (isNetVork) {
                    if (mCurrentCounter < max_items) {
                        page++;
                        videoPlayerHttp.getVideoAnswer(vid, page);
                    } else {
                        video_answer_recyclerview.setNoMore(true);
                    }
                } else {
                    video_answer_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            videoPlayerHttp.getVideoAnswer(vid, page);
                        }
                    });
                }
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(videoAskAnswerAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getActivity(), VideoAnswerActivity.class).putExtras(bundle));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    closeMenu(view);
                    Animation animator = AnimationUtils.loadAnimation(getActivity(), R.anim.popupwindow_hidden_anim);
                    more_msg.setVisibility(View.GONE);
                    more_msg.startAnimation(animator);
                } else {
                    openMenu(view);
                    Animation animator = AnimationUtils.loadAnimation(getActivity(), R.anim.pupwindow_show_anim);
                    more_msg.setVisibility(View.VISIBLE);
                    more_msg.startAnimation(animator);
                }
            }
        });
        video_more_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(getActivity(), WriteNoteActivity.class).putExtras(bundle));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), QuestionActivity.class).putExtras(bundle));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), CommmentActivity.class).putExtras(bundle));
                        break;
                }
            }
        });
    }

    private void createData() {
        data = new ArrayList<>();
        item = new HashMap<>();
        item.put("img", R.drawable.answer_note);
        item.put("title", "记笔记");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.answer_ask);
        item.put("title", "提问");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.answer_comment);
        item.put("title", "评价");
        data.add(item);

//        item = new HashMap<>();
//        item.put("img", R.drawable.answer_lx);
//        item.put("title", "在线考试");
//        data.add(item);
//
//        item = new HashMap<>();
//        item.put("img", R.drawable.answer_share);
//        item.put("title", "分享");
//        data.add(item);
    }

    private void initView() {
        fab = getActivity().findViewById(R.id.fab);
        video_more_gridview = getActivity().findViewById(R.id.video_more_gridview);
        more_msg = getActivity().findViewById(R.id.more_msg);
        video_answer_recyclerview = getActivity().findViewById(R.id.video_answer_recyclerview);
        video_answer_nodata = getActivity().findViewById(R.id.video_answer_nodata);
    }

    public void openMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, -155, -135);
        animator.setDuration(500);
        animator.start();
        isOpen = true;
    }

    public void closeMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", -135, 20, 0);
        animator.setDuration(500);
        animator.start();
        isOpen = false;
    }

    public void getVideoAnswer(Response<ResponseBean<VideoAskAnswerBean>> response) {
        ResponseBean<VideoAskAnswerBean> data = response.body();
        if (isRefresh) {
            videoAskAnswerAdapter.clear();
        }
        max_items = data.data.getCount();
        items = data.data.getQuestion();
        if (data.data.getQuestion().size() > 0) {
            addItems(data.data.getQuestion());
            video_answer_nodata.setVisibility(View.GONE);
        } else {
            video_answer_nodata.setVisibility(View.VISIBLE);
        }
    }

    public void getVideoAnswerError(Response<ResponseBean<VideoAskAnswerBean>> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

    }

    public void getVideoAnswerFinish() {
        video_answer_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        isRefresh = false;
    }

    private void addItems(List<VideoAskAnswerBean.getVideoAnswerItems> list) {
        videoAskAnswerAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ZkwPreference.getInstance(getActivity()).IsRefresh()) {
            video_answer_recyclerview.refresh();
            ZkwPreference.getInstance(getActivity()).setIsRefresh(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            vid = ZkwPreference.getInstance(getActivity()).getVideoId();
            video_answer_recyclerview.refresh();
        }else {
            if (videoAskAnswerAdapter != null){
                videoAskAnswerAdapter.clear();
            }
        }
    }
}
