package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.MainNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IMainNewsModel;

import java.util.Map;

public class MainNewsModel implements IMainNewsModel{
    @Override
    public void getMainNews(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<MainNewsBean>> basicCallback) {
        JsonCallback<ResponseBean<MainNewsBean>> jsonCallback = new JsonCallback<ResponseBean<MainNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MainNewsBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<MainNewsBean>> response) {
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
