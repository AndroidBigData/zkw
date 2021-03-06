package com.zjwam.zkw.videoplayer.more;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.WriteNoteActivityHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.ZkwPreference;

public class WriteNoteActivity extends BaseActivity {
    private ImageView write_note_back;
    private TextView write_note_save;
    private EditText write_note;
    private String id, uid, vid, note,vtime;
    private WriteNoteActivityHttp writeNoteActivityHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        initView();
        initData();
    }

    private void initData() {
        String time = ZkwPreference.getInstance(getBaseContext()).getViteoTime();
        vtime = getTime(time);
        id = getIntent().getStringExtra("id");
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        vid = ZkwPreference.getInstance(getBaseContext()).getVideoId();

        writeNoteActivityHttp = new WriteNoteActivityHttp(this);
        write_note_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        write_note_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note = write_note.getText().toString();
                if (uid.trim().length() > 0) {
                    if (note.length() > 0) {
                        writeNoteActivityHttp.getWriteNoteMsg(uid, id, vid, vtime, note);
                    } else {
                        Toast.makeText(getBaseContext(), "输入内容为空！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getWriteNoteMsg(Response<ResponseBean<EmptyBean>> response) {
        write_note.setText("");
        Toast.makeText(getBaseContext(), response.body().msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void getWriteNoteMsgError(Response<ResponseBean<EmptyBean>> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void getWriteNoteMsgFinish() {
        ZkwPreference.getInstance(this).setViteoTime(String.valueOf(0));
//        ZkwPreference.getInstance(this).setVideoId("");
    }

    private String getTime(String time) {
        if (time.length()<4) {
            return "0";
        } else {
            return time.substring(0, time.length() - 3);
        }
    }

    private void initView() {
        write_note_back = findViewById(R.id.write_note_back);
        write_note_save = findViewById(R.id.write_note_save);
        write_note = findViewById(R.id.write_note);
    }

}
