package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ClassNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IClassNewsModel;

import java.util.Map;

public class ClassNewsModel implements IClassNewsModel{
    @Override
    public void getNews(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ClassNewsBean>> basicCallback) {
        JsonCallback<ResponseBean<ClassNewsBean>> jsonCallback = new JsonCallback<ResponseBean<ClassNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ClassNewsBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ClassNewsBean>> response) {
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
