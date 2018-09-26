package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ClassNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ClassNewsModel;
import com.zjwam.zkw.mvp.model.imodel.IClassNewsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IClassNewsPresenter;
import com.zjwam.zkw.mvp.view.IClassNewsView;
import com.zjwam.zkw.util.Config;

public class ClassNewsPresenter implements IClassNewsPresenter {
    private Context context;
    private IClassNewsView classNewsView;
    private IClassNewsModel classNewsModel;

    public ClassNewsPresenter(Context context, IClassNewsView classNewsView) {
        this.context = context;
        this.classNewsView = classNewsView;
        classNewsModel = new ClassNewsModel();
    }

    @Override
    public void getNews() {
        classNewsModel.getNews(Config.URL + "api/news/classes", context, null, new BasicCallback<ResponseBean<ClassNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ClassNewsBean>> response) {
                ClassNewsBean classNewsBean = response.body().data;
                classNewsView.setNews(classNewsBean.getNewClass(),classNewsBean.getTeacher(),classNewsBean.getGoodClass(),classNewsBean.getHotClass());
            }

            @Override
            public void onError(Response<ResponseBean<ClassNewsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                classNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
