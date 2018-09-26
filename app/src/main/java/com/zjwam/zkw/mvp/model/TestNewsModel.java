package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.ClassTypeInfo;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.TestNewsBean;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.ITestNewsModel;

import java.util.List;
import java.util.Map;

public class TestNewsModel implements ITestNewsModel {
    @Override
    public void getInfo(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<ClassTypeInfo>>> basicCallback) {
        Json2Callback<ResponseBean<List<ClassTypeInfo>>> json2Callback = new Json2Callback<ResponseBean<List<ClassTypeInfo>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ClassTypeInfo>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<ClassTypeInfo>>> response) {
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

    @Override
    public void queryResult(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<TestQueryResultDialogBean>>> basicCallback) {
        Json2Callback<ResponseBean<List<TestQueryResultDialogBean>>> json2Callback = new Json2Callback<ResponseBean<List<TestQueryResultDialogBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<TestQueryResultDialogBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<TestQueryResultDialogBean>>> response) {
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

    @Override
    public void getNews(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<TestNewsBean>> basicCallback) {
        Json2Callback<ResponseBean<TestNewsBean>> json2Callback = new Json2Callback<ResponseBean<TestNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<TestNewsBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<TestNewsBean>> response) {
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
