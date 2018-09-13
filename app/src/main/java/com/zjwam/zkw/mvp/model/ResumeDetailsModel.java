package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumeDetailsBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IResumeDetailsModel;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Map;

public class ResumeDetailsModel implements IResumeDetailsModel {
    @Override
    public void getDetails(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ResumeDetailsBean>> basicCallback) {
        JsonCallback<ResponseBean<ResumeDetailsBean>> jsonCallback = new JsonCallback<ResponseBean<ResumeDetailsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumeDetailsBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ResumeDetailsBean>> response) {
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
