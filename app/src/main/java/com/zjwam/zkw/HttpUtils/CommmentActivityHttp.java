package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.more.CommmentActivity;

public class CommmentActivityHttp {
    private Context context;

    public CommmentActivityHttp(Context context) {
        this.context = context;
    }
    public void upDataMsg(String uid,String vid,String id,String comment,String numStars) {
        OkGo.<ResponseBean<EmptyBean>>post(Config.URL + "api/play/comment_add")
                .params("uid", uid)
                .params("vid", vid)
                .params("id", id)
                .params("comment", comment)
                .params("star", numStars)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<EmptyBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                        if (context instanceof CommmentActivity){
                            ((CommmentActivity)context).upDataMsg(response);
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<EmptyBean>> response) {
                        super.onError(response);
                        if (context instanceof CommmentActivity){
                            ((CommmentActivity) context).upDataMsgError(response);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (context instanceof CommmentActivity){
                            ((CommmentActivity) context).upDataMsgFinish();
                        }
                    }
                });
    }

}
