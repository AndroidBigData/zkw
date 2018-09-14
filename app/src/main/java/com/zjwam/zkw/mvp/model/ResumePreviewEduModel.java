package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewEduBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IResumePreviewEduModel;

import java.util.Map;

public class ResumePreviewEduModel implements IResumePreviewEduModel {
    @Override
    public void getPreviewEdu(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<ResumePreviewEduBean>> basicCallback) {
        JsonCallback<ResponseBean<ResumePreviewEduBean>> jsonCallback = new JsonCallback<ResponseBean<ResumePreviewEduBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePreviewEduBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePreviewEduBean>> response) {
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
