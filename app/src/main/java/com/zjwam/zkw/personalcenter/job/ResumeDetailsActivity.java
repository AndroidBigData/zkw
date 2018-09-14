package com.zjwam.zkw.personalcenter.job;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ResumeDetailsAdapter;
import com.zjwam.zkw.entity.ResumeDetailsBean;
import com.zjwam.zkw.mvp.presenter.ReumeDetailsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumeDetailsPresenter;
import com.zjwam.zkw.mvp.view.IResumeDetailsView;

public class ResumeDetailsActivity extends BaseActivity implements IResumeDetailsView{

    private ImageView back;
    private TextView title,resume_name,resume_details_type,resume_details_job,resume_details_time,resume_details_money,resume_details_name,resume_details_sex,
            resume_details_age,resume_details_phone,resume_details_comment,resume_details_skill;
    private LRecyclerView resume_details_recyclerview;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ResumeDetailsAdapter resumeDetailsAdapter;
    private IResumeDetailsPresenter resumeDetailsPresenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_details);
        initView();
        initData();
    }

    private void initData() {
        title.setText("简历详情");
        id = getIntent().getExtras().getString("id");
        resumeDetailsPresenter = new ReumeDetailsPresenter(this,this);
        resumeDetailsPresenter.getDetails(id);
        resumeDetailsAdapter = new ResumeDetailsAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(resumeDetailsAdapter);
        resume_details_recyclerview.setAdapter(lRecyclerViewAdapter);
        resume_details_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        resume_details_recyclerview.setLoadMoreEnabled(false);
        resume_details_recyclerview.setPullRefreshEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("type", String.valueOf(resumeDetailsAdapter.getDataList().get(position).getType()));
                bundle.putString("id", String.valueOf(resumeDetailsAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getBaseContext(),ResumePreviewActivity.class).putExtras(bundle));
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        resume_name = findViewById(R.id.resume_name);
        resume_details_type = findViewById(R.id.resume_details_type);
        resume_details_job = findViewById(R.id.resume_details_job);
        resume_details_time = findViewById(R.id.resume_details_time);
        resume_details_money = findViewById(R.id.resume_details_money);
        resume_details_name = findViewById(R.id.resume_details_name);
        resume_details_sex = findViewById(R.id.resume_details_sex);
        resume_details_age = findViewById(R.id.resume_details_age);
        resume_details_phone = findViewById(R.id.resume_details_phone);
        resume_details_comment = findViewById(R.id.resume_details_comment);
        resume_details_skill = findViewById(R.id.resume_details_skill);
        resume_details_recyclerview = findViewById(R.id.resume_details_recyclerview);
    }

    @Override
    public void setDetails(ResumeDetailsBean data) {
        resume_name.setText(data.getResume_name());
        resume_details_type.setText(data.getJob_type());
        resume_details_job.setText(data.getIndustry_id());
        resume_details_time.setText(data.getHiredate());
        resume_details_money.setText(data.getSalary());
        resume_details_name.setText(data.getUser_name());
        resume_details_sex.setText(data.getGender());
        resume_details_age.setText(String.valueOf(data.getAge()));
        resume_details_phone.setText(data.getPhone());
        resume_details_comment.setText(data.getEvaluate());
        resume_details_skill.setText(data.getSkill());
        resumeDetailsAdapter.addAll(data.getOther());
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }
}
