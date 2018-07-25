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

import java.util.HashMap;
import java.util.Map;

public class CommmentActivityHttp {
    private Context context;
    private Map<String,String> params;

    public CommmentActivityHttp(Context context) {
        this.context = context;
    }
    public void upDataMsg(String uid,String vid,String id,String comment,String numStars) {
        params = new HashMap<>();
        params.put("uid", uid);
        params.put("vid", vid);
        params.put("id", id);
        params.put("comment", comment);
        params.put("star", numStars);

        OkGoUtils.postRequets(Config.URL + "api/play/comment_add", context, params, new JsonCallback<ResponseBean<EmptyBean>>() {
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
