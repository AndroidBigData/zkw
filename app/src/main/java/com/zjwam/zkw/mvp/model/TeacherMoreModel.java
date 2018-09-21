package com.zjwam.zkw.mvp.model;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.TeacherMoreBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.ITeacherMoreModel;

import java.util.Map;

public class TeacherMoreModel implements ITeacherMoreModel {
    @Override
    public void getInfo(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<TeacherMoreBean>> basicCallback) {
        JsonCallback<ResponseBean<TeacherMoreBean>> jsonCallback = new JsonCallback<ResponseBean<TeacherMoreBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<TeacherMoreBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<TeacherMoreBean>> response) {
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
