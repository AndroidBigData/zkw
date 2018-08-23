package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamRecordBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IExamRecordModel;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Map;

public class ExamRecordModel implements IExamRecordModel {
    @Override
    public void getRecordItem(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ExamRecordBean>> basicCallback) {
        JsonCallback<ResponseBean<ExamRecordBean>> jsonCallback = new JsonCallback<ResponseBean<ExamRecordBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamRecordBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ExamRecordBean>> response) {
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
    public void deleteRecord(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<EmptyBean>> basicCallback) {
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
