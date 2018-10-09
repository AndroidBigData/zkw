package com.zjwam.zkw.fragment.news;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ClassNewsAdapter;
import com.zjwam.zkw.entity.ClassNewsBean;
import com.zjwam.zkw.mvp.presenter.ClassNewsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IClassNewsPresenter;
import com.zjwam.zkw.mvp.view.IClassNewsView;
import com.zjwam.zkw.news.ClassNewsMoreActivity;
import com.zjwam.zkw.news.NewsActivity;
import com.zjwam.zkw.news.TeacherMoreActivity;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassNewsFragment extends Fragment implements IClassNewsView{
    private Context context;
    private ClassNewsAdapter newClassAdapter,teacherAdapter,fineClassAdapter,hotClassAdapter;
    private RecyclerView updata_recyclerview,teacher_recyclerview,fine_recyclerview,hot_recyclerview;
    private TextView updata_more,fine_more,hot_more;
    private IClassNewsPresenter classNewsPresenter;
    private String updata_id,fine_id,hot_id;

    public ClassNewsFragment() {
        // Required empty public constructor
    }

    public static ClassNewsFragment newInstance(Context context) {
        ClassNewsFragment fragment = new ClassNewsFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        classNewsPresenter = new ClassNewsPresenter(context,this);
        classNewsPresenter.getNews();
        newClassAdapter = new ClassNewsAdapter(context,0);
        teacherAdapter = new ClassNewsAdapter(context,1);
        fineClassAdapter = new ClassNewsAdapter(context,0);
        hotClassAdapter = new ClassNewsAdapter(context,0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);


        DividerItemDecoration divider = new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider_white));
        updata_recyclerview.addItemDecoration(divider);
        teacher_recyclerview.addItemDecoration(divider);
        fine_recyclerview.addItemDecoration(divider);
        hot_recyclerview.addItemDecoration(divider);

        updata_recyclerview.setLayoutManager(linearLayoutManager);
        teacher_recyclerview.setLayoutManager(linearLayoutManager1);
        fine_recyclerview.setLayoutManager(linearLayoutManager2);
        hot_recyclerview.setLayoutManager(linearLayoutManager3);
        updata_recyclerview.setAdapter(newClassAdapter);
        teacher_recyclerview.setAdapter(teacherAdapter);
        fine_recyclerview.setAdapter(fineClassAdapter);
        hot_recyclerview.setAdapter(hotClassAdapter);

        newClassAdapter.setOnClickListener(new ClassNewsAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                jump2Class(newClassAdapter,position);
            }
        });

        teacherAdapter.setOnClickListener(new ClassNewsAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(teacherAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getActivity(), TeacherMoreActivity.class).putExtras(bundle));
            }
        });

        fineClassAdapter.setOnClickListener(new ClassNewsAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                jump2Class(fineClassAdapter,position);
            }
        });

        hotClassAdapter.setOnClickListener(new ClassNewsAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                jump2Class(hotClassAdapter,position);
            }
        });
        updata_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2More(updata_id,"课程更新");
            }
        });
        fine_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2More(fine_id,"精品课程");
            }
        });
        hot_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2More(hot_id,"热播课程");
            }
        });
    }

    private void jump2More(String id,String title){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("title",title);
        startActivity(new Intent(getActivity(),ClassNewsMoreActivity.class).putExtras(bundle));
    }

    private void jump2Class(ClassNewsAdapter adapter,int position){
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(adapter.getDataList().get(position).getId()));
        bundle.putString("title", "课程详情");
        Intent intent = new Intent(getActivity(), Video2PlayActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initView() {
        updata_recyclerview = getActivity().findViewById(R.id.updata_recyclerview);
        teacher_recyclerview = getActivity().findViewById(R.id.teacher_recyclerview);
        fine_recyclerview = getActivity().findViewById(R.id.fine_recyclerview);
        hot_recyclerview = getActivity().findViewById(R.id.hot_recyclerview);
        updata_more = getActivity().findViewById(R.id.updata_more);
        fine_more = getActivity().findViewById(R.id.fine_more);
        hot_more = getActivity().findViewById(R.id.hot_more);
    }

    @Override
    public void setNews(List<ClassNewsBean.Item> newClass, List<ClassNewsBean.Item> teacher, List<ClassNewsBean.Item> fineClass, List<ClassNewsBean.Item> hotClass) {
        newClassAdapter.addAll(newClass);
        teacherAdapter.addAll(teacher);
        fineClassAdapter.addAll(fineClass);
        hotClassAdapter.addAll(hotClass);
        if (newClass.size()>0){
            updata_id = String.valueOf(newClass.get(0).getCate_id());
        }
        if (fineClass.size()>0){
            fine_id = String.valueOf(fineClass.get(0).getCate_id());
        }
        if (hotClass.size()>0){
            hot_id = String.valueOf(hotClass.get(0).getCate_id());
        }
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof NewsActivity){
            ((NewsActivity) context).error(msg);
        }
    }
}
