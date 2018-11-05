package com.zjwam.zkw.videoplayer.more;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.httputils.CommmentActivityHttp;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.ZkwPreference;

public class CommmentActivity extends BaseActivity {

    private RatingBar comment_rat_bar;
    private TextView comment_save;
    private EditText comment_text;
    private ImageView comment_back;
    private float numStars;
    private String comment, uid = "", vid, id;
    private CommmentActivityHttp commmentActivityHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commment);
        initView();
        initData();
    }

    private void initData() {
        commmentActivityHttp = new CommmentActivityHttp(this);
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        vid = ZkwPreference.getInstance(getBaseContext()).getVideoId();
        id = getIntent().getStringExtra("id");
        numStars = comment_rat_bar.getRating();
        comment_rat_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                numStars = ratingBar.getRating();
                if (comment_rat_bar.getNumStars() != numStars) {
                    comment_rat_bar.setRating(numStars);
                }
            }
        });
        comment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        comment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment = comment_text.getText().toString();
                if (uid.trim().length() > 0) {
                    if (comment.trim().length() > 0) {
                        commmentActivityHttp.upDataMsg(uid, vid, id, comment, String.valueOf(numStars));
                    } else {
                        Toast.makeText(getBaseContext(), "请输入内容！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void upDataMsg(Response<ResponseBean<EmptyBean>> response) {
        Toast.makeText(getBaseContext(), response.body().msg, Toast.LENGTH_SHORT).show();
        comment_rat_bar.setRating(5);
        comment_text.setText("");
        finish();
    }

    public void upDataMsgError(Response<ResponseBean<EmptyBean>> response) {
        Throwable exception = response.getException();
//        if (exception instanceof MyException) {
//            Toast.makeText(getBaseContext(), ((MyException) exception).getErrorBean().msg, Toast.LENGTH_SHORT).show();
//        }
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }
    public void upDataMsgFinish() {
//        ZkwPreference.getInstance(this).setVideoId("");
    }

    private void initView() {
        comment_rat_bar = findViewById(R.id.comment_rat_bar);
        comment_save = findViewById(R.id.comment_save);
        comment_text = findViewById(R.id.comment_text);
        comment_back = findViewById(R.id.comment_back);
    }
}
