package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.CollectionExamBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ExamCollectionModel;
import com.zjwam.zkw.mvp.model.imodel.IExamCollectionModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamCollectionPresenter;
import com.zjwam.zkw.mvp.view.IExamCollectionView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ExamCollectionPresenter implements IExamCollectionPresenter {
    private Context context;
    private IExamCollectionView examCollectionView;
    private IExamCollectionModel examCollectionModel;
    private Map<String,String> param;

    public ExamCollectionPresenter(Context context, IExamCollectionView examCollectionView) {
        this.context = context;
        this.examCollectionView = examCollectionView;
        examCollectionModel = new ExamCollectionModel();
    }

    @Override
    public void getCollectionItem(final boolean isRefresh, String page) {
        param = new HashMap<>();
        param.put("uid",examCollectionModel.uid(context));
        param.put("page",page);
        examCollectionModel.getCollrctionItem(Config.URL + "api/user/examHold", context, param, new BasicCallback<ResponseBean<CollectionExamBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<CollectionExamBean>> response) {
                if (isRefresh){
                    examCollectionView.refresh();
                }
                CollectionExamBean examBean = response.body().data;
                examCollectionView.onSuccess(examBean.getHold(),examBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<CollectionExamBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examCollectionView.showMsg(error);
            }

            @Override
            public void onFinish() {
                examCollectionView.freshComplete();
            }
        });
    }
}
