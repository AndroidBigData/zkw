package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.MainNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.MainNewsModel;
import com.zjwam.zkw.mvp.model.imodel.IMainNewsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMainNewsPresenter;
import com.zjwam.zkw.mvp.view.IMainNewsView;
import com.zjwam.zkw.util.Config;

public class MainNewsPresenter implements IMainNewsPresenter {
    private Context context;
    private IMainNewsView mainNewsView;
    private IMainNewsModel mainNewsModel;

    public MainNewsPresenter(Context context, IMainNewsView mainNewsView) {
        this.context = context;
        this.mainNewsView = mainNewsView;
        mainNewsModel = new MainNewsModel();
    }

    @Override
    public void getMainNews() {
        mainNewsModel.getMainNews(Config.URL + "api/news/top", context, null, new BasicCallback<ResponseBean<MainNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MainNewsBean>> response) {
                MainNewsBean mainNewsBean = response.body().data;
                mainNewsView.setNews(mainNewsBean.getCate0(),mainNewsBean.getCate1());
            }

            @Override
            public void onError(Response<ResponseBean<MainNewsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                mainNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
