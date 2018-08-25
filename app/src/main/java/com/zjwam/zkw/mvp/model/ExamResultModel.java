package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamBaseResultBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IExamResultModel;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Map;

public class ExamResultModel implements IExamResultModel {
    @Override
    public void getExamResult(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ExamBaseResultBean>> basicCallback) {
        JsonCallback<ResponseBean<ExamBaseResultBean>> jsonCallback = new JsonCallback<ResponseBean<ExamBaseResultBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamBaseResultBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ExamBaseResultBean>> response) {
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
    public String uid(Context context) {
        return ZkwPreference.getInstance(context).getUid();
    }

    @Override
    public void getExamTestHold(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<EmptyBean>> basicCallback) {
        JsonCallback<ResponseBean<EmptyBean>> jsonCallback = new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
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
