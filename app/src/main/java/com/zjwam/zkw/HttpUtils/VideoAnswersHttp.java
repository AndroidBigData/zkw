package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VideoAnswersBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.VideoAnswerActivity;

public class VideoAnswersHttp {
    private Context context;
    public VideoAnswersHttp(Context context) {
        this.context = context;
    }
    public void getAnswers(String id,int page){
        OkGo.<ResponseBean<VideoAnswersBean>>post(Config.URL+"api/play/question_detial")
                .params("id",id)
                .params("page",page)
                .tag(context)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<VideoAnswersBean>>() {
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
}
