package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.CurriculumSelectBean;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumeIdBean;
import com.zjwam.zkw.entity.ResumePickerBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.ICreateResumeModel;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CreateResumeModel implements ICreateResumeModel {
    @Override
    public void getRexumeChoiceInfo(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ResumePickerBean>> basicCallback) {
        JsonCallback<ResponseBean<ResumePickerBean>> jsonCallback = new JsonCallback<ResponseBean<ResumePickerBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePickerBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePickerBean>> response) {
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
    public void getProfession(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<ProfessionChoiceBean>>> basicCallback) {
        JsonCallback<ResponseBean<List<ProfessionChoiceBean>>> jsonCallback = new JsonCallback<ResponseBean<List<ProfessionChoiceBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ProfessionChoiceBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<ProfessionChoiceBean>>> response) {
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
    public void saveResume(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ResumeIdBean>> basicCallback) {
        JsonCallback<ResponseBean<ResumeIdBean>> jsonCallback = new JsonCallback<ResponseBean<ResumeIdBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumeIdBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ResumeIdBean>> response) {
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
}
