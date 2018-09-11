package com.zjwam.zkw.personalcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;

public class AddJobEducationalActivity extends BaseActivity {

    private String resumeId;
    private ImageView back;
    private TextView title,add_enrolment_go,add_education_name,add_enrolment_time,add_graduation_time;
    private EditText add_school_name,add_major_name;
    private LinearLayout add_education,add_enrolment,add_graduation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_educational);
        initView();
        initData();
    }

    private void initData() {
        title.setText("添加教育背景");
        back.setVisibility(View.GONE);
        resumeId = getIntent().getExtras().getString("resumeId");
        add_enrolment_go.setOnClickListener(onClickListener);
        add_education.setOnClickListener(onClickListener);
        add_enrolment.setOnClickListener(onClickListener);
        add_graduation.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_enrolment_go:
                    break;
                case R.id.add_education:
                    break;
                case R.id.add_enrolment:
                    break;
                case R.id.add_graduation:
                    break;
            }
        }
    };
    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        add_enrolment_go = findViewById(R.id.add_enrolment_go);
        add_education_name = findViewById(R.id.add_education_name);
        add_enrolment_time = findViewById(R.id.add_enrolment_time);
        add_graduation_time = findViewById(R.id.add_graduation_time);
        add_school_name = findViewById(R.id.add_school_name);
        add_major_name = findViewById(R.id.add_major_name);
        add_education = findViewById(R.id.add_education);
        add_enrolment = findViewById(R.id.add_enrolment);
        add_graduation = findViewById(R.id.add_graduation);
    }
}
