package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VideoAnswersBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.VideoAnswerActivity;

import java.util.HashMap;
import java.util.Map;

public class VideoAnswersHttp {
    private Context context;
    private Map<String,String> params;
    public VideoAnswersHttp(Context context) {
        this.context = context;
    }
    public void getAnswers(String id,int page){
        params = new HashMap<>();
        params.put("id",id);
        params.put("page", String.valueOf(page));
        OkGoUtils.postRequets(Config.URL + "api/play/question_detial", context, params, new JsonCallback<ResponseBean<VideoAnswersBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<VideoAnswersBean>> response) {
                if (context instanceof VideoAnswerActivity){
                    ((VideoAnswerActivity) context).getAnswers(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<VideoAnswersBean>> response) {
                super.onError(response);
                if (context instanceof VideoAnswerActivity){
                    ((VideoAnswerActivity) context).getAnswersError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof VideoAnswerActivity){
                    ((VideoAnswerActivity) context).getAnswersFinish();
                }
            }
        });
    }
    public void getAnswersReply(String id, String uid, String content){
        params.put("id",id);
        params.put("uid",uid);
        params.put("content",content);
        OkGoUtils.postRequets(Config.URL + "api/play/question_add", context, params, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof VideoAnswerActivity){
                    ((VideoAnswerActivity) context).getAnswersReply(response);
                }
            }
            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof VideoAnswerActivity){
                    ((VideoAnswerActivity) context).getAnswersReplyError(response);
                }
            }
        });
    }
}
