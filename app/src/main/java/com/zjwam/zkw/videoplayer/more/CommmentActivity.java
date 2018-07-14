package com.zjwam.zkw.videoplayer.more;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;

public class CommmentActivity extends BaseActivity {

    private RatingBar comment_rat_bar;
    private TextView comment_save;
    private EditText comment_text;
    private ImageView comment_back;
    private float numStars;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commment);
        initView();
        initData();
    }

    private void initData() {
        comment_rat_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                numStars = ratingBar.getRating();
                if (comment_rat_bar.getNumStars() != numStars){
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
                Log.i("---comment:",comment+";"+numStars);
            }
        });
    }

    private void initView() {
        comment_rat_bar = findViewById(R.id.comment_rat_bar);
        comment_save = findViewById(R.id.comment_save);
        comment_text = findViewById(R.id.comment_text);
        comment_back = findViewById(R.id.comment_back);
    }
}
