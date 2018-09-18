package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.JobDetailsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.MineDeliveryModel;
import com.zjwam.zkw.mvp.model.imodel.IMineDeliveryModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMineDeliveryPresenter;
import com.zjwam.zkw.mvp.view.IMineDeliveryView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class MineDeliveryPresenter implements IMineDeliveryPresenter {
    private Context context;
    private IMineDeliveryView deliveryView;
    private IMineDeliveryModel deliveryModel;
    private Map<String,String> param;

    public MineDeliveryPresenter(Context context, IMineDeliveryView deliveryView) {
        this.context = context;
        this.deliveryView = deliveryView;
        deliveryModel = new MineDeliveryModel();
    }

    @Override
    public void getDelivery(String id) {
        param = new HashMap<>();
        param.put("uid",deliveryModel.uid(context));
        param.put("id",id);
        deliveryModel.getDelivery(Config.URL + "api/resume/myJob", context, param, new BasicCallback<ResponseBean<JobDetailsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobDetailsBean>> response) {
                deliveryView.setJobDetails(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<JobDetailsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                deliveryView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
