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

public class WriteNoteActivityHttp {
    private Context context;

    public WriteNoteActivityHttp(Context context) {
        this.context = context;
    }
    public void getWriteNoteMsg(String uid,String id,String vid,String vtime,String note) {
        OkGo.<ResponseBean<EmptyBean>>post(Config.URL + "api/play/note ")
                .params("uid", uid)
                .params("id", id)
                .params("vid", vid)
                .params("vtime", vtime)
                .params("note", note)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<EmptyBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                        if (context instanceof WriteNoteActivity){
                            ((WriteNoteActivity) context).getWriteNoteMsg(response);
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<EmptyBean>> response) {
                        super.onError(response);
                        if (context instanceof WriteNoteActivity){
                            ((WriteNoteActivity) context).getWriteNoteMsgError(response);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (context instanceof WriteNoteActivity){
                            ((WriteNoteActivity) context).getWriteNoteMsgFinish();
                    }
                    }
                });
    }
}
