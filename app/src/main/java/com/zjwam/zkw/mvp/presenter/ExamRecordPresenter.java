package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamRecordBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ExamRecordModel;
import com.zjwam.zkw.mvp.model.imodel.IExamRecordModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamRecordPresenter;
import com.zjwam.zkw.mvp.view.IExamRecordView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ExamRecordPresenter implements IExamRecordPresenter {
    private Context context;
    private IExamRecordView examRecordView;
    private IExamRecordModel examRecordModel;
    private Map<String,String> param;

    public ExamRecordPresenter(Context context, IExamRecordView examRecordView) {
        this.context = context;
        this.examRecordView = examRecordView;
        examRecordModel = new ExamRecordModel();
    }

    @Override
    public void getRecodItem(String page, final boolean isRefresh) {
        param = new HashMap<>();
        param.put("uid",examRecordModel.uid(context));
        param.put("page",page);
        examRecordModel.getRecordItem(Config.URL + "api/user/exam", context, param, new BasicCallback<ResponseBean<ExamRecordBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamRecordBean>> response) {
                if (isRefresh){
                    examRecordView.refresh();
                }
                ExamRecordBean examRecordBean = response.body().data;
                examRecordView.addRecordItem(examRecordBean.getRecord(),examRecordBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<ExamRecordBean>> response) {
                Throwable exception = response.getException();
                String error  = HttpErrorMsg.getErrorMsg(exception);
                examRecordView.showMsg(error);
            }

            @Override
            public void onFinish() {
                examRecordView.refreshComplele();
            }
        });
    }

    @Override
    public void deleteRecord(String id) {
        param = new HashMap<>();
        param.put("uid",examRecordModel.uid(context));
        param.put("id",id);
        examRecordModel.deleteRecord(Config.URL + "api/user/examDel", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                examRecordView.deleteRecord();
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examRecordView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
