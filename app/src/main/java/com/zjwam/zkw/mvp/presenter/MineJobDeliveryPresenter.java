package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.MineJobDeliveryBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.MineJobDeliveryModel;
import com.zjwam.zkw.mvp.model.imodel.IMineJobDeliveryModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMineJobDeliveryPresenter;
import com.zjwam.zkw.mvp.view.IMineJobDeliveryView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class MineJobDeliveryPresenter implements IMineJobDeliveryPresenter {
    private Context context;
    private IMineJobDeliveryView jobDeliveryView;
    private IMineJobDeliveryModel jobDeliveryModel;
    private Map<String,String> param;

    public MineJobDeliveryPresenter(Context context, IMineJobDeliveryView jobDeliveryView) {
        this.context = context;
        this.jobDeliveryView = jobDeliveryView;
        jobDeliveryModel = new MineJobDeliveryModel();
    }

    @Override
    public void getJobDelivery(String page, final boolean isRefresh) {
        param = new HashMap<>();
        param.put("uid",jobDeliveryModel.uid(context));
        param.put("page",page);
        jobDeliveryModel.getJobDelivery(Config.URL + "api/user/resumeSend", context, param, new BasicCallback<ResponseBean<MineJobDeliveryBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineJobDeliveryBean>> response) {
                if (isRefresh){
                    jobDeliveryView.refresh();
                }
                MineJobDeliveryBean jobDeliveryBean = response.body().data;
                jobDeliveryView.addRecordItem(jobDeliveryBean.getResume(),jobDeliveryBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<MineJobDeliveryBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobDeliveryView.showMsg(error);
            }

            @Override
            public void onFinish() {
                jobDeliveryView.refreshComplele();
            }
        });
    }
}
