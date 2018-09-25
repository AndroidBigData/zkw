package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SearchClassBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IClassNewsMoreModel;

import java.util.Map;

public class ClassNewsMoreModel implements IClassNewsMoreModel {
    @Override
    public void getNews(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<SearchClassBean>> basicCallback) {
        Json2Callback<ResponseBean<SearchClassBean>> json2Callback = new Json2Callback<ResponseBean<SearchClassBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<SearchClassBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<SearchClassBean>> response) {
                super.onError(response);
                basicCallback.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                basicCallback.onFinish();
            }
        };
        OkGoUtils.postRequets(url,context,param,json2Callback);
    }
}
