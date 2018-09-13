package com.zjwam.zkw.personalcenter.job;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.mvp.presenter.AddJobProjectPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IAddJobProjectPresenter;
import com.zjwam.zkw.mvp.view.IAddJobProjectView;
import com.zjwam.zkw.util.BasicPickerView;
import com.zjwam.zkw.util.KeyboardUtils;

public class AddJobProjectActivity extends BaseActivity implements IAddJobProjectView{

    private ImageView back;
    private TextView title,add_project_go,add_project_jump,add_project_begintime,add_project_endtime;
    private EditText add_project_name,add_project_describe,add_project_duty;
    private LinearLayout add_begintime,add_endtime;
    private Button addProject;
    private String resumeId;
    private BasicPickerView pickerView;
    private IAddJobProjectPresenter projectPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_project);
        initView();
        initData();
    }

    private void initData() {
        resumeId = getIntent().getExtras().getString("resumeId");
        back.setVisibility(View.GONE);
        title.setText("添加项目经验");
        projectPresenter = new AddJobProjectPresenter(this,this);
        add_project_go.setOnClickListener(onClickListener);
        add_project_jump.setOnClickListener(onClickListener);
        add_begintime.setOnClickListener(onClickListener);
        add_endtime.setOnClickListener(onClickListener);
        addProject.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_project_go:
                    upProject(0);
                    break;
                case R.id.add_project_jump:
                    finish();
                    break;
                case R.id.add_begintime:
                    KeyboardUtils.hideKeyboard(add_begintime);
                    if (pickerView ==null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.timePicker(AddJobProjectActivity.this);
                    pickerView.setTimePicker(new BasicPickerView.TimePicker() {
                        @Override
                        public void Picker(String date) {
                            add_project_begintime.setText(date);
                        }
                    });
                    break;
                case R.id.add_endtime:
                    KeyboardUtils.hideKeyboard(add_endtime);
                    if (pickerView ==null){
                        pickerView = new BasicPickerView();
                    }
                    pickerView.timePicker(AddJobProjectActivity.this);
                    pickerView.setTimePicker(new BasicPickerView.TimePicker() {
                        @Override
                        public void Picker(String date) {
                            add_project_endtime.setText(date);
                        }
                    });
                    break;
                case R.id.addProject:
                    upProject(1);
                    break;
            }
        }
    };

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        add_project_go = findViewById(R.id.add_project_go);
        add_project_jump = findViewById(R.id.add_project_jump);
        add_project_begintime = findViewById(R.id.add_project_begintime);
        add_project_endtime = findViewById(R.id.add_project_endtime);
        add_project_name = findViewById(R.id.add_project_name);
        add_project_describe = findViewById(R.id.add_project_describe);
        add_project_duty = findViewById(R.id.add_project_duty);
        add_begintime = findViewById(R.id.add_begintime);
        add_endtime = findViewById(R.id.add_endtime);
        addProject = findViewById(R.id.addProject);
    }

    private void upProject(int type){
        if (add_project_name.getText().toString().length()>0&&add_project_begintime.getText().toString().length()>0&&add_project_endtime.getText().toString().length()>0&&
                add_project_describe.getText().toString().length()>0&&add_project_duty.getText().toString().length()>0){
            projectPresenter.saveProject(type,resumeId,add_project_name.getText().toString(),add_project_begintime.getText().toString(),add_project_endtime.getText().toString()
            ,add_project_describe.getText().toString(),add_project_duty.getText().toString());
        }else {
            error("请完善项目经验！");
        }
    }

    @Override
    public void saveProjectGo() {
        finish();
    }

    @Override
    public void saveProjectMore() {
        add_project_begintime.setText("");
        add_project_endtime.setText("");
        add_project_name.setText("");
        add_project_describe.setText("");
        add_project_duty.setText("");
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }
}
