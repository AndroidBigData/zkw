package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VersionBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IVersionControlModel;

import java.util.Map;

public class VersionControlModel implements IVersionControlModel {
    @Override
    public void versionControl(String url, Object context, final BasicCallback<ResponseBean<VersionBean>> basicCallback) {
        JsonCallback<ResponseBean<VersionBean>> jsonCallback = new JsonCallback<ResponseBean<VersionBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<VersionBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<VersionBean>> response) {
                super.onError(response);
                basicCallback.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                basicCallback.onFinish();
            }
        };
        OkGoUtils.getRequets(url,context,jsonCallback);
    }
}
