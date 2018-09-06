package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.HotCityBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.ISearchJobHotCityModel;

import java.util.List;
import java.util.Map;

public class SearchJobHotCityModel implements ISearchJobHotCityModel {
    @Override
    public void getHotCity(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<List<HotCityBean>>> basicCallback) {
        JsonCallback<ResponseBean<List<HotCityBean>>> jsonCallback = new JsonCallback<ResponseBean<List<HotCityBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<HotCityBean>>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<List<HotCityBean>>> response) {
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
