package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.JobEmplomentBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IJobEmploymentModel;

import java.util.Map;

public class JobEmploymentModel implements IJobEmploymentModel {
    @Override
    public void getEmployment(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<JobEmplomentBean>> basicCallback) {
        JsonCallback<ResponseBean<JobEmplomentBean>> jsonCallback = new JsonCallback<ResponseBean<JobEmplomentBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobEmplomentBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<JobEmplomentBean>> response) {
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
