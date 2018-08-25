package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ExamTestCollectionBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ExamTestCollectionModel;
import com.zjwam.zkw.mvp.model.imodel.IExamTestCollectionModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamTestCollectionPresenter;
import com.zjwam.zkw.mvp.view.IExamTestCollectionView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ExamTestCollectionPresenter implements IExamTestCollectionPresenter {
    private Context context;
    private IExamTestCollectionView examTestCollectionView;
    private IExamTestCollectionModel examTestCollectionModel;
    private Map<String,String> param;

    public ExamTestCollectionPresenter(Context context, IExamTestCollectionView examTestCollectionView) {
        this.context = context;
        this.examTestCollectionView = examTestCollectionView;
        examTestCollectionModel = new ExamTestCollectionModel();
    }

    @Override
    public void getTestCollection(final boolean isRefresh, String page) {
        param = new HashMap<>();
        param.put("uid",examTestCollectionModel.uid(context));
        param.put("page",page);
        examTestCollectionModel.getTestCollrctionItem(Config.URL + "api/user/paperHold", context, param, new BasicCallback<ResponseBean<ExamTestCollectionBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamTestCollectionBean>> response) {
                if (isRefresh){
                    examTestCollectionView.refresh();
                }
                ExamTestCollectionBean examTestCollectionBean = response.body().data;
                examTestCollectionView.onSuccess(examTestCollectionBean.getPaper(),examTestCollectionBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<ExamTestCollectionBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examTestCollectionView.showMsg(error);
            }

            @Override
            public void onFinish() {
                examTestCollectionView.freshComplete();
            }
        });
    }
}
