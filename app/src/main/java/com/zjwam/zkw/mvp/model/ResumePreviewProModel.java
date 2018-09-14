package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewProBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IResumePreviewProModel;

import java.util.Map;

public class ResumePreviewProModel implements IResumePreviewProModel {
    @Override
    public void getPreviewEdu(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ResumePreviewProBean>> basicCallback) {
        JsonCallback<ResponseBean<ResumePreviewProBean>> jsonCallback = new JsonCallback<ResponseBean<ResumePreviewProBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePreviewProBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePreviewProBean>> response) {
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
