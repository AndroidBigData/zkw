package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IJobHomeModel;

import java.util.Map;

public class JobHomeModel implements IJobHomeModel {
    @Override
    public void getJobItem(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<JobHomeBean>> basicCallback) {
        JsonCallback<ResponseBean<JobHomeBean>> jsonCallback = new JsonCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
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
