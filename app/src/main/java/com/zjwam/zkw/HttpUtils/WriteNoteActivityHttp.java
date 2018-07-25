package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.more.WriteNoteActivity;

import java.util.HashMap;
import java.util.Map;

public class WriteNoteActivityHttp {
    private Context context;
    private Map<String, String> param;

    public WriteNoteActivityHttp(Context context) {
        this.context = context;
    }

    public void getWriteNoteMsg(String uid, String id, String vid, String vtime, String note) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("id", id);
        param.put("vid", vid);
        param.put("vtime", vtime);
        param.put("note", note);
        OkGoUtils.postRequets(Config.URL + "api/play/note ", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof WriteNoteActivity) {
                    ((WriteNoteActivity) context).getWriteNoteMsg(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof WriteNoteActivity) {
                    ((WriteNoteActivity) context).getWriteNoteMsgError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof WriteNoteActivity) {
                    ((WriteNoteActivity) context).getWriteNoteMsgFinish();
                }
            }
        });
    }
}
