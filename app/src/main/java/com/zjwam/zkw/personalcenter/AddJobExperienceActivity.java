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
import com.zjwam.zkw.mvp.presenter.AddJobExperiencePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IAddJobExpericencePresenter;
import com.zjwam.zkw.mvp.view.IAddJobExperienceView;
import com.zjwam.zkw.util.BasicPickerView;
import com.zjwam.zkw.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

public class AddJobExperienceActivity extends BaseActivity implements IAddJobExperienceView{

    private String resumeId;
    private ImageView back;
    private TextView title,add_experience_jump,add_experience_go,add_work_begintime,add_work_endtime,add_type;
    private EditText add_company_name,add_experience_job,add_experience_money,add_experience_describe;
    private LinearLayout add_begintime,add_endtime,add_company_type;
    private Button addExperience;
    private List<ResumePickerBean.BasicInfo> choiceInfo;
    private List<String> item;
    private BasicPickerView pickerView;
    private String typeId;
    private IAddJobExpericencePresenter expericencePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_experience);
        initView();
        initData();
    }

    private void initData() {
        resumeId = getIntent().getExtras().getString("resumeId");
        back.setVisibility(View.GONE);
        title.setText("添加工作经历");
        expericencePresenter = new AddJobExperiencePresenter(this,this);
        expericencePresenter.getChoiceInfo();
        add_begintime.setOnClickListener(onClickListener);
        add_endtime.setOnClickListener(onClickListener);
        add_company_type.setOnClickListener(onClickListener);
        add_experience_jump.setOnClickListener(onClickListener);
        add_experience_go.setOnClickListener(onClickListener);
        addExperience.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_begintime:
                    KeyboardUtils.hideKeyboard(add_begintime);
                    if (pickerView == null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.timePicker(AddJobExperienceActivity.this);
                    pickerView.setTimePicker(new BasicPickerView.TimePicker() {
                        @Override
                        public void Picker(String date) {
                            add_work_begintime.setText(date);
                        }
                    });
                    break;
                case R.id.add_endtime:
                    KeyboardUtils.hideKeyboard(add_endtime);
                    if (pickerView == null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.timePicker(AddJobExperienceActivity.this);
                    pickerView.setTimePicker(new BasicPickerView.TimePicker() {
                        @Override
                        public void Picker(String date) {
                            add_work_endtime.setText(date);
                        }
                    });
                    break;
                case R.id.add_company_type:
                    KeyboardUtils.hideKeyboard(add_company_type);
                    if (pickerView == null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.pickerVIew(AddJobExperienceActivity.this,null,item,null,null);
                    pickerView.getSelctor(new BasicPickerView.Selctor() {
                        @Override
                        public void Options(int options1, int options2, int options3, View view) {
                            add_type.setText(item.get(options1));
                            typeId = String.valueOf(choiceInfo.get(options1).getId());
                        }
                    });
                    break;
                case R.id.add_experience_jump:
                    Bundle bundle = new Bundle();
                    bundle.putString("resumeId",resumeId);
                    startActivity(new Intent(getBaseContext(),AddJobProjectActivity.class).putExtras(bundle));
                    finish();
                    break;
                case R.id.add_experience_go:
                    upInfo(0);
                    break;
                case R.id.addExperience:
                    upInfo(1);
                    break;
            }
        }
    };
    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        add_experience_jump = findViewById(R.id.add_experience_jump);
        add_experience_go = findViewById(R.id.add_experience_go);
        add_work_begintime = findViewById(R.id.add_work_begintime);
        add_work_endtime = findViewById(R.id.add_work_endtime);
        add_company_name = findViewById(R.id.add_company_name);
        add_company_type = findViewById(R.id.add_company_type);
        add_experience_job = findViewById(R.id.add_experience_job);
        add_experience_money = findViewById(R.id.add_experience_money);
        add_experience_describe = findViewById(R.id.add_experience_describe);
        add_begintime = findViewById(R.id.add_begintime);
        add_endtime = findViewById(R.id.add_endtime);
        addExperience = findViewById(R.id.addExperience);
        add_type = findViewById(R.id.add_type);
    }

    private void upInfo(int type){
        if (add_company_name.getText().toString().length()>0&&add_work_begintime.getText().toString().length()>0&&add_work_endtime.getText().toString().length()>0&&
        add_type.getText().toString().length()>0&&add_experience_job.getText().toString().length()>0&&add_experience_money.getText().toString().length()>0){
            expericencePresenter.saveExpericence(type,resumeId,add_company_name.getText().toString(),add_work_begintime.getText().toString(),
                    add_work_endtime.getText().toString(),typeId,add_experience_job.getText().toString(),add_experience_money.getText().toString(),
                    add_experience_describe.getText().toString());
        }else {
            error("请完善工作经验！");
        }
    }

    @Override
    public void getChoiceInfo(List<ResumePickerBean.BasicInfo> list) {
        choiceInfo = list;
        item = new ArrayList<>();
        for (ResumePickerBean.BasicInfo info:list) {
            item.add(info.getName());
        }
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void saveExperienceAgain() {
        Bundle bundle = new Bundle();
        bundle.putString("resumeId",resumeId);
        startActivity(new Intent(getBaseContext(),AddJobProjectActivity.class).putExtras(bundle));
        finish();
    }

    @Override
    public void saveExperienceMore() {
        add_company_name.setText("");
        add_work_begintime.setText("");
        add_work_endtime.setText("");
        add_type.setText("");
        add_experience_job.setText("");
        add_experience_money.setText("");
        add_experience_describe.setText("");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            Bundle bundle = new Bundle();
            bundle.putString("resumeId",resumeId);
            startActivity(new Intent(getBaseContext(),AddJobProjectActivity.class).putExtras(bundle));
            finish();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
