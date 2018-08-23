package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ExamDetailsBean;
import com.zjwam.zkw.entity.ExamUpBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IExamDetailsModel;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ExamDetailsModel implements IExamDetailsModel {
    @Override
    public void getExamDetails(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<ExamDetailsBean>>> basicCallback) {
        JsonCallback<ResponseBean<List<ExamDetailsBean>>> jsonCallback = new JsonCallback<ResponseBean<List<ExamDetailsBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ExamDetailsBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<ExamDetailsBean>>> response) {
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
    public void upaExam(String url, Object context, Map<String, String> param, JSONObject json,final BasicCallback<ResponseBean<ExamUpBean>> basicCallback) {
        JsonCallback<ResponseBean<ExamUpBean>> jsonCallback = new JsonCallback<ResponseBean<ExamUpBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamUpBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ExamUpBean>> response) {
                super.onError(response);
                basicCallback.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                basicCallback.onFinish();
            }
        };
        OkGoUtils.postJSON(url,context,param,json,jsonCallback);
    }
}
