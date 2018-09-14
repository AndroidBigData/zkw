package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewJobBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IResumePreviewJobModel;

import java.util.Map;

public class ResumePreviewJobModel implements IResumePreviewJobModel{
    @Override
    public void getPreviewEdu(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ResumePreviewJobBean>> basicCallback) {
        JsonCallback<ResponseBean<ResumePreviewJobBean>> jsonCallback = new JsonCallback<ResponseBean<ResumePreviewJobBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePreviewJobBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePreviewJobBean>> response) {
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
