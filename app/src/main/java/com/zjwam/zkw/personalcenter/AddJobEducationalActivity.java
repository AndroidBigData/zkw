package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ResumePickerBean;
import com.zjwam.zkw.mvp.presenter.AddJobEducationPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IAddJobEducationPresenter;
import com.zjwam.zkw.mvp.view.IAddJobEducationView;
import com.zjwam.zkw.util.BasicPickerView;
import com.zjwam.zkw.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

public class AddJobEducationalActivity extends BaseActivity implements IAddJobEducationView{

    private String resumeId;
    private ImageView back;
    private TextView title,add_education_go,add_education_name,add_enrolment_time,add_graduation_time,add_education_jump;
    private EditText add_school_name,add_major_name;
    private LinearLayout add_education,add_enrolment,add_graduation;
    private BasicPickerView pickerView;
    private List<String> education;
    private List<ResumePickerBean.BasicInfo> educationList;
    private IAddJobEducationPresenter educationPresenter;
    private Button addEducation;
    private String school_nature;
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
        educationPresenter = new AddJobEducationPresenter(this,this);
        educationPresenter.getEducaion();
        resumeId = getIntent().getExtras().getString("resumeId");
        add_education_go.setOnClickListener(onClickListener);
        add_education.setOnClickListener(onClickListener);
        add_enrolment.setOnClickListener(onClickListener);
        add_graduation.setOnClickListener(onClickListener);
        addEducation.setOnClickListener(onClickListener);
        add_education_jump.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_education_go:
                    saveEducationInfo(0);
                    break;
                case R.id.add_education:
                    KeyboardUtils.hideKeyboard(add_education);
                    if (education.size()>0){
                        initPicker(education,null,null);
                        pickerView.getSelctor(new BasicPickerView.Selctor() {
                            @Override
                            public void Options(int options1, int options2, int options3, View view) {
                                add_education_name.setText(education.get(options1));
                                school_nature = String.valueOf(educationList.get(options1).getId());
                            }
                        });
                    }
                    break;
                case R.id.add_enrolment:
                    KeyboardUtils.hideKeyboard(add_enrolment);
                    if (pickerView == null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.timePicker(AddJobEducationalActivity.this);
                    pickerView.setTimePicker(new BasicPickerView.TimePicker() {
                        @Override
                        public void Picker(String date) {
                            add_enrolment_time.setText(date);
                        }
                    });
                    break;
                case R.id.add_graduation:
                    KeyboardUtils.hideKeyboard(add_graduation);
                    if (pickerView == null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.timePicker(AddJobEducationalActivity.this);
                    pickerView.setTimePicker(new BasicPickerView.TimePicker() {
                        @Override
                        public void Picker(String date) {
                            add_graduation_time.setText(date);
                        }
                    });
                    break;
                case R.id.addEducation:
                    saveEducationInfo(1);
                    break;
                case R.id.add_education_jump:
                    Bundle bundle = new Bundle();
                    bundle.putString("resumeId",resumeId);
                    startActivity(new Intent(getBaseContext(),AddJobExperienceActivity.class).putExtras(bundle));
                    finish();
                    break;
            }
        }
    };
    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        add_education_go = findViewById(R.id.add_education_go);
        add_education_name = findViewById(R.id.add_education_name);
        add_enrolment_time = findViewById(R.id.add_enrolment_time);
        add_graduation_time = findViewById(R.id.add_graduation_time);
        add_school_name = findViewById(R.id.add_school_name);
        add_major_name = findViewById(R.id.add_major_name);
        add_education = findViewById(R.id.add_education);
        add_enrolment = findViewById(R.id.add_enrolment);
        add_graduation = findViewById(R.id.add_graduation);
        addEducation = findViewById(R.id.addEducation);
        add_education_jump = findViewById(R.id.add_education_jump);
    }

    private void saveEducationInfo(int type){
        if (add_school_name.getText().toString().trim().length()>0&&add_major_name.getText().toString().trim().length()>0
                && add_education_name.getText().toString().trim().length()>0&&add_enrolment_time.getText().toString().length()>0
                && add_graduation_time.getText().toString().length()>0){
            educationPresenter.saveEducation(type,resumeId,add_school_name.getText().toString(),school_nature,add_major_name.getText().toString(),
                    add_enrolment_time.getText().toString(),add_graduation_time.getText().toString());
        }else {
            error("请完善教育信息！");
        }
    }

    @Override
    public void getEducation(List<ResumePickerBean.BasicInfo> list) {
        education = new ArrayList<>();
        educationList = list;
        for (ResumePickerBean.BasicInfo name : list ){
            education.add(name.getName());
        }
    }
    private <T> void initPicker(List<T> item1, List<List<T>> item2, List<List<List<T>>> item3) {
        if (pickerView == null){
            pickerView = new BasicPickerView();
        }
        pickerView.pickerVIew(AddJobEducationalActivity.this, null, item1, item2, item3);
    }
    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void saveEducation() {
        Bundle bundle = new Bundle();
        bundle.putString("resumeId",resumeId);
        startActivity(new Intent(getBaseContext(),AddJobExperienceActivity.class).putExtras(bundle));
        finish();
    }

    @Override
    public void goEducation() {
        add_education_name.setText("");
        add_enrolment_time.setText("");
        add_graduation_time.setText("");
        add_school_name.setText("");
        add_major_name.setText("");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            Bundle bundle = new Bundle();
            bundle.putString("resumeId",resumeId);
            startActivity(new Intent(getBaseContext(),AddJobExperienceActivity.class).putExtras(bundle));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
