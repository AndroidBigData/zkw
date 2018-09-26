package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.JobNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IJobNewsModel;

import java.util.Map;

public class JobNewsModel implements IJobNewsModel {
    @Override
    public void getNews(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<JobNewsBean>> basicCallback) {
        Json2Callback<ResponseBean<JobNewsBean>> json2Callback = new Json2Callback<ResponseBean<JobNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobNewsBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<JobNewsBean>> response) {
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
