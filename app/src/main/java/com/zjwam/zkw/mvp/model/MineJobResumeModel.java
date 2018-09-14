package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineJobResumeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IMineJobResumeModel;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;
import java.util.Map;

public class MineJobResumeModel implements IMineJobResumeModel {
    @Override
    public void getJobResume(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<MineJobResumeBean>>> basicCallback) {
        JsonCallback<ResponseBean<List<MineJobResumeBean>>> jsonCallback = new JsonCallback<ResponseBean<List<MineJobResumeBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<MineJobResumeBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<MineJobResumeBean>>> response) {
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
    public void deleteResume(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<EmptyBean>>> basicCallback) {
        JsonCallback<ResponseBean<List<EmptyBean>>> jsonCallback = new JsonCallback<ResponseBean<List<EmptyBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<EmptyBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<EmptyBean>>> response) {
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
