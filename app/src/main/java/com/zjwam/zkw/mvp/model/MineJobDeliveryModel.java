package com.zjwam.zkw.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.MineJobDeliveryBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.OkGoUtils;
import com.zjwam.zkw.mvp.model.imodel.IMineJobDeliveryModel;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Map;

public class MineJobDeliveryModel implements IMineJobDeliveryModel {
    @Override
    public void getJobDelivery(String url, Object context, Map<String, String> param, final BasicCallback<ResponseBean<MineJobDeliveryBean>> basicCallback) {
        JsonCallback<ResponseBean<MineJobDeliveryBean>> jsonCallback = new JsonCallback<ResponseBean<MineJobDeliveryBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineJobDeliveryBean>> response) {
                basicCallback.onSuccess(response);
            }

            @Override
            public void onError(Response<ResponseBean<MineJobDeliveryBean>> response) {
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

    @Override
    public String uid(Context context) {
        return ZkwPreference.getInstance(context).getUid();
    }
}
