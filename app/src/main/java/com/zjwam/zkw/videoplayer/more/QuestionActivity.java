package com.zjwam.zkw.videoplayer.more;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.HttpUtils.VideoPlayerHttp;
import com.zjwam.zkw.HttpUtils.VideoQuestionHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

public class QuestionActivity extends BaseActivity {

    private ImageView question_back;
    private TextView question_save;
    private EditText question_content;
    private String vid,uid,vtime,id,content;
    private VideoQuestionHttp videoQuestionHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();
        initData();
    }

    private void initData() {
        vid = ZkwPreference.getInstance(getBaseContext()).getVideoId();
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        vtime = ZkwPreference.getInstance(getBaseContext()).getViteoTime();
        id = getIntent().getStringExtra("id");
        videoQuestionHttp = new VideoQuestionHttp(this);
        question_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        question_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = question_content.getText().toString().trim();
                if (ZkwPreference.getInstance(getBaseContext()).IsFlag()){
                    if (content.length()>0){
                        videoQuestionHttp.getQuextion(uid,id,vid,vtime,content);
                    }else {
                        Toast.makeText(getBaseContext(),"输入内容为空！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getBaseContext(),"请先登录！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getQuestion(Response<ResponseBean<EmptyBean>> response){
        Toast.makeText(getBaseContext(),response.body().msg,Toast.LENGTH_SHORT).show();
        question_content.setText("");
        ZkwPreference.getInstance(getBaseContext()).setIsRefresh(true);
    }
    public void getQuestionError(Response<ResponseBean<EmptyBean>> response){
        Throwable exception = response.getException();
        if (exception instanceof MyException){
            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
        }
    }
    public void getQuestionFinish(){
        ZkwPreference.getInstance(getBaseContext()).setVideoId("");
        ZkwPreference.getInstance(getBaseContext()).setViteoTime("0");
    }

    private void initView() {
        question_back = findViewById(R.id.question_back);
        question_save = findViewById(R.id.question_save);
        question_content = findViewById(R.id.question_content);
    }
}