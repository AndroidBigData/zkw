package com.zjwam.zkw.httputils;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.more.QuestionActivity;

import java.util.HashMap;
import java.util.Map;

public class VideoQuestionHttp {
    private Context context;
    private Map<String,String> params;
    public VideoQuestionHttp(Context context) {
        this.context = context;
    }
    public void getQuextion(String uid,String id,String vid,String time,String content){
        params = new HashMap<>();
        params.put("uid",uid);
        params.put("id",id);
        params.put("vid",vid);
        params.put("time",time);
        params.put("content",content);
        OkGoUtils.postRequets(Config.URL + "api/play/question_run", context, params, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof QuestionActivity){
                    ((QuestionActivity) context).getQuestion(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof QuestionActivity){
                    ((QuestionActivity) context).getQuestionError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof QuestionActivity){
                    ((QuestionActivity) context).getQuestionFinish();
                }
            }
        });
    }
}
