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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.zjwam.zkw.R;
import com.zjwam.zkw.videoplayer.more.CommmentActivity;
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
    private String id = "";
    private Bundle bundle;
    private Context context;

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
        bundle.putString("id",id);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen){
                    closeMenu(view);
                    Animation animator = AnimationUtils.loadAnimation(getActivity(),R.anim.popupwindow_hidden_anim);
                    more_msg.setVisibility(View.GONE);
                    more_msg.startAnimation(animator);
                }else {
                    openMenu(view);
                    Animation  animator = AnimationUtils.loadAnimation(getActivity(),R.anim.pupwindow_show_anim);
                    more_msg.setVisibility(View.VISIBLE);
                    more_msg.startAnimation(animator);
                }
            }
        });
        video_more_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(getActivity(), WriteNoteActivity.class).putExtras(bundle));
                        break;
                    case 1:
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

        item = new HashMap<>();
        item.put("img", R.drawable.answer_load);
        item.put("title", "下载资料");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.answer_lx);
        item.put("title", "在线考试");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.answer_share);
        item.put("title", "分享");
        data.add(item);
    }

    private void initView() {
        fab = getActivity().findViewById(R.id.fab);
        video_more_gridview = getActivity().findViewById(R.id.video_more_gridview);
        more_msg = getActivity().findViewById(R.id.more_msg);
    }
    public void openMenu(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,-155,-135);
        animator.setDuration(500);
        animator.start();
        isOpen = true;
    }
    public void closeMenu(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",-135,20,0);
        animator.setDuration(500);
        animator.start();
        isOpen = false;
    }
}
