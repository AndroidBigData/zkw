package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ClassTypeInfo;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.TestNewsBean;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.TestNewsModel;
import com.zjwam.zkw.mvp.model.imodel.ITestNewsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.ITestNewsPresenter;
import com.zjwam.zkw.mvp.view.ITestNewsView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestNewsPresenter implements ITestNewsPresenter {
    private Context context;
    private ITestNewsView testNewsView;
    private ITestNewsModel testNewsModel;
    private Map<String,String> param;

    public TestNewsPresenter(Context context, ITestNewsView testNewsView) {
        this.context = context;
        this.testNewsView = testNewsView;
        testNewsModel = new TestNewsModel();
    }

    @Override
    public void getInfo() {
        testNewsModel.getInfo(Config.URL + "api/news/examinatiion", context, param, new BasicCallback<ResponseBean<List<ClassTypeInfo>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ClassTypeInfo>>> response) {
                testNewsView.setInfo(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<ClassTypeInfo>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                testNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void queryResult(String cid) {
        param = new HashMap<>();
        param.put("cid",cid);
        testNewsModel.queryResult(Config.URL + "api/news/examinatiion_lists", context, param, new BasicCallback<ResponseBean<List<TestQueryResultDialogBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<TestQueryResultDialogBean>>> response) {
                testNewsView.showDialog(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<TestQueryResultDialogBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                testNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void getNews(String city) {
        param = new HashMap<>();
        param.put("city",city);
        testNewsModel.getNews(Config.URL + "api/news/exam_first", context, param, new BasicCallback<ResponseBean<TestNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<TestNewsBean>> response) {
                testNewsView.refresh();
                TestNewsBean testNewsBean = response.body().data;
                testNewsView.setNews(testNewsBean.getCate8(),testNewsBean.getCate9(),testNewsBean.getCate10(),testNewsBean.getCate11(),testNewsBean.getCate12());
            }

            @Override
            public void onError(Response<ResponseBean<TestNewsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                testNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
