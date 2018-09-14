package com.zjwam.zkw.personalcenter.job;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.fragment.personalcenter.ResumePreviewEduFragment;
import com.zjwam.zkw.fragment.personalcenter.ResumePreviewJobFragment;
import com.zjwam.zkw.fragment.personalcenter.ResumePreviewProFragment;

public class ResumePreviewActivity extends BaseActivity {
    private String type;
    private ImageView back;
    private TextView title;
    private FragmentManager fragmentManager;
    private String id;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_preview);
        initView();
        initData();
    }

    private void initData() {
        type = getIntent().getExtras().getString("type");
        id = getIntent().getExtras().getString("id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (type){
            case "1":
                ResumePreviewEduFragment eduFragment = ResumePreviewEduFragment.newInstance(this,id);
                transaction.add(R.id.fragment,eduFragment);
                transaction.commit();
                title.setText("教育背景");
                break;
            case "2":
                ResumePreviewJobFragment jobFragment = ResumePreviewJobFragment.newInstance(this,id);
                transaction.add(R.id.fragment,jobFragment);
                transaction.commit();
                title.setText("工作经历");
                break;
            case "3":
                ResumePreviewProFragment proFragment = ResumePreviewProFragment.newInstance(this,id);
                transaction.add(R.id.fragment,proFragment);
                transaction.commit();
                title.setText("项目经验");
                break;
        }
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
    }
}
