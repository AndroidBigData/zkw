package com.zjwam.zkw.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.TeacherMoreAdapter;
import com.zjwam.zkw.entity.TeacherMoreBean;
import com.zjwam.zkw.mvp.presenter.TeacherMorePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.ITeacherMorePresenter;
import com.zjwam.zkw.mvp.view.ITeacherMoreView;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;
import java.util.Objects;

public class TeacherMoreActivity extends BaseActivity implements ITeacherMoreView{

    private ImageView back,teacher_more_img;
    private TextView title,teacher_more_name,teacher_more_jj,teacher_more_jianjie,teacher_more_rongyu;
    private RecyclerView more_recyclerview_rongyu,teacher_more_class;
    private TeacherMoreAdapter teacherMoreAdapter;
    private String id;
    private ITeacherMorePresenter teacherMorePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_more);
        initView();
        initData();
    }

    private void initData() {
        id = Objects.requireNonNull(getIntent().getExtras()).getString("id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("名师介绍");
        teacherMorePresenter = new TeacherMorePresenter(this,this);
        teacherMoreAdapter = new TeacherMoreAdapter(this);
        teacher_more_class.setAdapter(teacherMoreAdapter);
        teacher_more_class.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        teacherMorePresenter.getInfo(id);
        teacherMoreAdapter.setOnClickListener(new TeacherMoreAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(teacherMoreAdapter.getDataList().get(position).getId()));
                bundle.putString("title", "课程详情");
                Intent intent = new Intent(getBaseContext(), Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        teacher_more_img = findViewById(R.id.teacher_more_img);
        title = findViewById(R.id.title);
        teacher_more_name = findViewById(R.id.teacher_more_name);
        teacher_more_jj = findViewById(R.id.teacher_more_jj);
        teacher_more_jianjie = findViewById(R.id.teacher_more_jianjie);
        teacher_more_rongyu = findViewById(R.id.teacher_more_rongyu);
        more_recyclerview_rongyu = findViewById(R.id.more_recyclerview_rongyu);
        teacher_more_class = findViewById(R.id.teacher_more_class);
    }

    @Override
    public void setInfo(TeacherMoreBean.Teacher teacher, List<TeacherMoreBean.Classfor> classfor) {
        GlideImageUtil.setImageView(getBaseContext(),teacher.getImg(),teacher_more_img, RequestOptions.circleCropTransform());
        teacher_more_name.setText(teacher.getName());
        teacher_more_jj.setText(teacher.getSign());
        if (teacher.getInfo().contains("&nbsp;")){
            String info = teacher.getInfo().replace("&nbsp;"," ");
            teacher_more_jianjie.setText(info);
        }else {
            teacher_more_jianjie.setText(teacher.getInfo());
        }
        teacher_more_rongyu.setText(teacher.getHonor());
        teacherMoreAdapter.addAll(classfor);
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }
}
