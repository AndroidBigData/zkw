package com.zjwam.zkw.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;

import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.util.ZkwPreference;

public class RegisterChoiceActivity extends BaseActivity {

    private ConstraintLayout choice_back;
    private Button choice_student,choice_teacher,choice_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register_choice);
        initView();
        initData();
    }

    private void initData() {
        choice_back.setOnClickListener(onClickListener);
        choice_student.setOnClickListener(onClickListener);
        choice_teacher.setOnClickListener(onClickListener);
        choice_company.setOnClickListener(onClickListener);
    }

    private void initView() {
        choice_back = findViewById(R.id.choice_back);
        choice_student = findViewById(R.id.choice_student);
        choice_teacher = findViewById(R.id.choice_teacher);
        choice_company = findViewById(R.id.choice_company);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.choice_back:
                    finish();
                    break;
                case R.id.choice_student:
                    ZkwPreference.getInstance(getBaseContext()).SetRegisterType("0");
                    Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.choice_teacher:
                    ZkwPreference.getInstance(getBaseContext()).SetRegisterType("1");
                    Intent intent1 = new Intent(getBaseContext(),RegisterActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.choice_company:
                    ZkwPreference.getInstance(getBaseContext()).SetRegisterType("2");
                    Intent intent2 = new Intent(getBaseContext(),RegisterActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };
}
