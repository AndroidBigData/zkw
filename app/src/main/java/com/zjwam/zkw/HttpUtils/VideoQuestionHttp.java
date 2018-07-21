package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.more.QuestionActivity;

public class VideoQuestionHttp {
    private Context context;

    public VideoQuestionHttp(Context context) {
        this.context = context;
    }
    public void getQuextion(String uid,String id,String vid,String time,String content){
        OkGo.<ResponseBean<EmptyBean>>post(Config.URL+"api/play/question_run")
                .params("uid",uid)
                .params("id",id)
                .params("vid",vid)
                .params("time",time)
                .params("content",content)
                .tag(content)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<EmptyBean>>() {
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
