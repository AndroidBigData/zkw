package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IExamModel;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Map;

public class ExamModel implements IExamModel {
    @Override
    public void getExam(String url, Object context, Map<String,String> param,final BasicCallback<ResponseBean<ExamBean>> basicCallback) {
        JsonCallback<ResponseBean<ExamBean>> jsonCallback = new JsonCallback<ResponseBean<ExamBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ExamBean>> response) {
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
    public void getExamFL(String url, Object context, final BasicCallback<ClassSearchBean> basicCallback) {
        Json2Callback<ClassSearchBean> json2Callback = new Json2Callback<ClassSearchBean>() {
            @Override
            public void onSuccess(Response<ClassSearchBean> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ClassSearchBean> response) {
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
    public void getFLResult(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ExamBean>> basicCallback) {
        JsonCallback<ResponseBean<ExamBean>> jsonCallback = new JsonCallback<ResponseBean<ExamBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ExamBean>> response) {
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
    public void holdExam(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<EmptyBean>> basicCallback) {
        JsonCallback<ResponseBean<EmptyBean>> jsonCallback = new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
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
}
