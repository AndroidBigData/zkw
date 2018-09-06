package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IProfessionChoiceModel;

import java.util.List;
import java.util.Map;

public class ProfessionChoiceModel implements IProfessionChoiceModel {
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
}
