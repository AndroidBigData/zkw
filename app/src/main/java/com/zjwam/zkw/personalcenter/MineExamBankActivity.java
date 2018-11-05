package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;

public class MineExamBankActivity extends BaseActivity {

    private ImageView back;
    private TextView title,jump2Record,jump2exam,jump2text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_exam_bank);
        initView();
        initData();
    }

    private void initData() {
        title.setText("我的题库");
        back.setOnClickListener(onClickListener);
        jump2Record.setOnClickListener(onClickListener);
        jump2exam.setOnClickListener(onClickListener);
        jump2text.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.jump2Record:
                    startActivity(new Intent(getBaseContext(), ExamRecordActivity.class));
                    break;
                case R.id.jump2exam:
                    startActivity(new Intent(getBaseContext(), CollectionExamActivity.class));
                    break;
                case R.id.jump2text:
                    startActivity(new Intent(getBaseContext(), CollectionTestActivity.class));
                    break;
            }
        }
    };
    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        jump2Record = findViewById(R.id.jump2Record);
        jump2exam = findViewById(R.id.jump2exam);
        jump2text = findViewById(R.id.jump2text);
    }

}
