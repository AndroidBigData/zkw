package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.CurriculumSelectBean;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResumePickerBean;
import com.zjwam.zkw.mvp.presenter.CreateResumePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.ICreateResumePresenter;
import com.zjwam.zkw.mvp.view.ICreateResumeView;
import com.zjwam.zkw.util.BasicPickerView;
import com.zjwam.zkw.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

public class CreateResumeActivity extends BaseActivity implements ICreateResumeView {

    private EditText create_resume_name, create_name, create_age, create_phone, create_comment, create_skill, create_resume_moneyMin, create_resume_moneyMax;
    private ImageView back;
    private TextView title, create_resume_type, create_resume_jobType, create_resume_time, create_sex;
    private LinearLayout create_resume_typePicker, create_resume_jobTypePicker, create_resume_timePicker, create_resume_moneyPicker, create_sexPicker;
    private Button createResume;
    private BasicPickerView pickerView;
    private List<ResumePickerBean.BasicInfo> jobtypeList, hiredateList;
    private ICreateResumePresenter resumePresenter;
    private List<CurriculumSelectBean> item1 = new ArrayList<>();
    private List<List<CurriculumSelectBean>> item2 = new ArrayList<>();
    private List<List<List<CurriculumSelectBean>>> item3 = new ArrayList<>();
    private List<String> itemOne = new ArrayList<>();
    private List<List<String>> itemTwo = new ArrayList<>();
    private List<List<List<String>>> itemThree = new ArrayList<>();
    private List<String> sex;
    private String ResumeId, job_type, industry_id, hiredate, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resume);
        initView();
        initData();
    }

    private void initData() {
        title.setText("创建简历");
        resumePresenter = new CreateResumePresenter(this, this);
        resumePresenter.getResumeChoiceInfo();
        resumePresenter.getProfession();

        sex = new ArrayList<>();
        sex.add("男");
        sex.add("女");
        back.setOnClickListener(onClickListener);
        create_resume_typePicker.setOnClickListener(onClickListener);
        create_resume_jobTypePicker.setOnClickListener(onClickListener);
        create_resume_timePicker.setOnClickListener(onClickListener);
        create_resume_moneyPicker.setOnClickListener(onClickListener);
        create_sexPicker.setOnClickListener(onClickListener);
        createResume.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.create_resume_typePicker:
                    KeyboardUtils.hideKeyboard(create_resume_typePicker);
                    List<String> data = new ArrayList<>();
                    for (ResumePickerBean.BasicInfo item : jobtypeList) {
                        data.add(item.getName());
                    }
                    if (jobtypeList.size() > 0) {
                        initPicker(data, null, null);
                    }
                    pickerView.getSelctor(new BasicPickerView.Selctor() {
                        @Override
                        public void Options(int options1, int options2, int options3, View view) {
                            job_type = String.valueOf(jobtypeList.get(options1).getId());
                            create_resume_type.setText(jobtypeList.get(options1).getName());
                        }
                    });
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.create_resume_jobTypePicker:
                    KeyboardUtils.hideKeyboard(create_resume_jobTypePicker);
                    initPicker(itemOne, itemTwo, itemThree);
                    pickerView.getSelctor(new BasicPickerView.Selctor() {
                        @Override
                        public void Options(int options1, int options2, int options3, View view) {
                            industry_id = String.valueOf(item3.get(options1).get(options2).get(options3).getId());
                            create_resume_jobType.setText(item3.get(options1).get(options2).get(options3).getName());
                        }
                    });
                    break;
                case R.id.create_resume_timePicker:
                    KeyboardUtils.hideKeyboard(create_resume_timePicker);
                    List<String> data2 = new ArrayList<>();
                    for (ResumePickerBean.BasicInfo item : hiredateList) {
                        data2.add(item.getName());
                    }
                    if (hiredateList.size() > 0) {
                        initPicker(data2, null, null);
                    }
                    pickerView.getSelctor(new BasicPickerView.Selctor() {
                        @Override
                        public void Options(int options1, int options2, int options3, View view) {
                            hiredate = String.valueOf(hiredateList.get(options1).getId());
                            create_resume_time.setText(hiredateList.get(options1).getName());
                        }
                    });
                    break;
                case R.id.create_sexPicker:
                    KeyboardUtils.hideKeyboard(create_sexPicker);
                    initPicker(sex, null, null);
                    pickerView.getSelctor(new BasicPickerView.Selctor() {
                        @Override
                        public void Options(int options1, int options2, int options3, View view) {
                            create_sex.setText(sex.get(options1));
                            if ("女".equals(sex.get(options1))) {
                                gender = "2";
                            } else {
                                gender = "1";
                            }
                        }
                    });
                    break;
                case R.id.createResume:
                    if (create_resume_name.getText().toString().trim().length() > 0) {
                        if (create_resume_jobType.getText().toString().length() > 0) {
                            resumePresenter.saveResume(create_resume_name.getText().toString(), job_type, industry_id, hiredate, create_resume_moneyMin.getText().toString(),
                                    create_resume_moneyMax.getText().toString(), create_name.getText().toString(), gender, create_age.getText().toString(),
                                    create_phone.getText().toString(), create_comment.getText().toString(), create_skill.getText().toString());
                        } else {
                            error("请选择求职类型！");
                        }

                    } else {
                        error("简历名称不得为空！");
                        create_resume_name.setEnabled(true);
                    }
                    break;
            }
        }
    };

    private <T> void initPicker(List<T> item1, List<List<T>> item2, List<List<List<T>>> item3) {
        pickerView = new BasicPickerView();
        pickerView.pickerVIew(CreateResumeActivity.this, null, item1, item2, item3);
    }

    private void initView() {
        create_resume_name = findViewById(R.id.create_resume_name);
        create_name = findViewById(R.id.create_name);
        create_age = findViewById(R.id.create_age);
        create_phone = findViewById(R.id.create_phone);
        create_comment = findViewById(R.id.create_comment);
        create_skill = findViewById(R.id.create_skill);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        create_resume_type = findViewById(R.id.create_resume_type);
        create_resume_jobType = findViewById(R.id.create_resume_jobType);
        create_resume_time = findViewById(R.id.create_resume_time);
        create_resume_moneyMin = findViewById(R.id.create_resume_moneyMin);
        create_resume_moneyMax = findViewById(R.id.create_resume_moneyMax);
        create_sex = findViewById(R.id.create_sex);
        create_resume_typePicker = findViewById(R.id.create_resume_typePicker);
        create_resume_jobTypePicker = findViewById(R.id.create_resume_jobTypePicker);
        create_resume_timePicker = findViewById(R.id.create_resume_timePicker);
        create_resume_moneyPicker = findViewById(R.id.create_resume_moneyPicker);
        create_sexPicker = findViewById(R.id.create_sexPicker);
        createResume = findViewById(R.id.createResume);
    }

    @Override
    public void getResumeChoiceData(List<ResumePickerBean.BasicInfo> job_type, List<ResumePickerBean.BasicInfo> hiredate) {
        jobtypeList = job_type;
        hiredateList = hiredate;
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void setProfession(List<ProfessionChoiceBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CurriculumSelectBean industrySelectBean1 = new CurriculumSelectBean();
            industrySelectBean1.setName(list.get(i).getName());
            industrySelectBean1.setId((int) list.get(i).getId());
            item1.add(industrySelectBean1);
            itemOne.add(item1.get(i).getName());
            List<CurriculumSelectBean> listTwo = new ArrayList<>();
            List<String> list_a = new ArrayList<>();
            List<List<CurriculumSelectBean>> listThree = new ArrayList<>();
            List<List<String>> list_c = new ArrayList<>();
            for (int j = 0; j < list.get(i).getChild().size(); j++) {
                if (list.get(i).getChild().get(j).getName() != null && list.get(i).getChild().get(j).getName().length() > 0) {
                    CurriculumSelectBean industrySelectBean2 = new CurriculumSelectBean();
                    industrySelectBean2.setName(list.get(i).getName());
                    industrySelectBean2.setId((int) list.get(i).getId());
                    listTwo.add(industrySelectBean2);
                    list_a.add(industrySelectBean2.getName());
                }
                List<CurriculumSelectBean> lists = new ArrayList<>();
                List<String> list_b = new ArrayList<>();
                for (int k = 0; k < list.get(i).getChild().get(j).getChild().size(); k++) {
                    if (list.get(i).getChild().get(j).getChild().get(k).getName() != null && list.get(i).getChild().get(j).getChild().get(k).getName().length() > 0) {
                        CurriculumSelectBean industrySelectBean3 = new CurriculumSelectBean();
                        industrySelectBean3.setName(list.get(i).getChild().get(j).getChild().get(k).getName());
                        industrySelectBean3.setId((int) list.get(i).getChild().get(j).getChild().get(k).getId());
                        lists.add(industrySelectBean3);
                        list_b.add(industrySelectBean3.getName());
                    }
                }
                listThree.add(lists);
                list_c.add(list_b);
            }
            item2.add(listTwo);
            itemTwo.add(list_a);
            item3.add(listThree);
            itemThree.add(list_c);
        }
    }

    @Override
    public void saveResume(long id) {
        ResumeId = String.valueOf(id);
        Bundle bundle = new Bundle();
        bundle.putString("resumeId", ResumeId);
        startActivity(new Intent(getBaseContext(), AddJobEducationalActivity.class).putExtras(bundle));
        finish();
    }
}
