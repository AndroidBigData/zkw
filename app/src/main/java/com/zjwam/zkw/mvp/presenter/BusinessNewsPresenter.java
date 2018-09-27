package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.BusinessNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.BusinessNewsModel;
import com.zjwam.zkw.mvp.model.imodel.IBusinessNewsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IBusinessNewsPresenter;
import com.zjwam.zkw.mvp.view.IBusinessNewsView;
import com.zjwam.zkw.util.Config;

public class BusinessNewsPresenter implements IBusinessNewsPresenter {
    private Context context;
    private IBusinessNewsView businessNewsView;
    private IBusinessNewsModel businessNewsModel;

    public BusinessNewsPresenter(Context context, IBusinessNewsView businessNewsView) {
        this.context = context;
        this.businessNewsView = businessNewsView;
        businessNewsModel = new BusinessNewsModel();
    }

    @Override
    public void getNews() {
        businessNewsModel.getNews(Config.URL + "api/news/work", context, new BasicCallback<ResponseBean<BusinessNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<BusinessNewsBean>> response) {
                BusinessNewsBean businessNewsBean = response.body().data;
                businessNewsView.setNews(businessNewsBean.getCate16(),businessNewsBean.getCate17(),businessNewsBean.getCate18(),businessNewsBean.getCate19(),businessNewsBean.getCate20());
            }

            @Override
            public void onError(Response<ResponseBean<BusinessNewsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                businessNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
