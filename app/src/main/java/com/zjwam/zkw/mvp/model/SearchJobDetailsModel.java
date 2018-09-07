package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.ISearchJobDetailsModel;

import java.util.Map;

public class SearchJobDetailsModel implements ISearchJobDetailsModel {
    @Override
    public void getSearchPop(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<SearchJobDetailsPopBean>> basicCallback) {
        JsonCallback<ResponseBean<SearchJobDetailsPopBean>> jsonCallback = new JsonCallback<ResponseBean<SearchJobDetailsPopBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<SearchJobDetailsPopBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<SearchJobDetailsPopBean>> response) {
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

    @Override
    public void getSearchJob(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<JobHomeBean>> basicCallback) {
        JsonCallback<ResponseBean<JobHomeBean>> jsonCallback = new JsonCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
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

    @Override
    public void getSearchJobText(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<JobHomeBean>> basicCallback) {
        JsonCallback<ResponseBean<JobHomeBean>> jsonCallback = new JsonCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
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
