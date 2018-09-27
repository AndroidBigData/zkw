package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.BusinessNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IBusinessNewsModel;

import java.util.Map;

public class BusinessNewsModel implements IBusinessNewsModel {
    @Override
    public void getNews(String url, Object context, final BasicCallback<ResponseBean<BusinessNewsBean>> basicCallback) {
        Json2Callback<ResponseBean<BusinessNewsBean>> json2Callback = new Json2Callback<ResponseBean<BusinessNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<BusinessNewsBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<BusinessNewsBean>> response) {
                super.onError(response);
                basicCallback.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                basicCallback.onFinish();
            }
        };
        OkGoUtils.getRequets(url,context,json2Callback);
    }
}
