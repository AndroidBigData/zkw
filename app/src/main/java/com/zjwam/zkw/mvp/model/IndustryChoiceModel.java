package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.IndustryChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IIndustryChoiceModel;

import java.util.List;
import java.util.Map;

public class IndustryChoiceModel implements IIndustryChoiceModel{
    @Override
    public void getIndustry(String url, Object context, Map<String,String> param, final BasicCallback<ResponseBean<List<IndustryChoiceBean>>> basicCallback) {
        JsonCallback<ResponseBean<List<IndustryChoiceBean>>> jsonCallback = new JsonCallback<ResponseBean<List<IndustryChoiceBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<IndustryChoiceBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<IndustryChoiceBean>>> response) {
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
