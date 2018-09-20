package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.NewsMoreBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.INewsMoreModel;

import java.util.Map;

public class NewsMoreModel implements INewsMoreModel {
    @Override
    public void getNews(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<NewsMoreBean>> basicCallback) {
        JsonCallback<ResponseBean<NewsMoreBean>> jsonCallback = new JsonCallback<ResponseBean<NewsMoreBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<NewsMoreBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<NewsMoreBean>> response) {
                super.onError(response);
                basicCallback.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                basicCallback.onFinish();
            }
        };
        OkGoUtils.postRequets(url,context,param,jsonCallback);
    }
}
