package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ExamBaseResultBean;
import com.zjwam.zkw.entity.ExamResultBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ExamResultModel;
import com.zjwam.zkw.mvp.model.imodel.IExamResultModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamResultPresenter;
import com.zjwam.zkw.mvp.view.IExamResultView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamResultPresenter implements IExamResultPresenter {
    private Context context;
    private IExamResultView examResultView;
    private IExamResultModel examResultModel;
    private Map<String,String> param;

    public ExamResultPresenter(Context context, IExamResultView examResultView) {
        this.context = context;
        this.examResultView = examResultView;
        examResultModel = new ExamResultModel();
    }

    @Override
    public void getExamResult(String id, String eid, final boolean isRefresh) {
        param = new HashMap<>();
        param.put("uid",examResultModel.uid(context));
        param.put("id",id);
        param.put("eid",eid);
        examResultModel.getExamResult(Config.URL + "api/exam/show", context, param, new BasicCallback<ResponseBean<ExamBaseResultBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamBaseResultBean>> response) {
                if (isRefresh){
                    examResultView.refresh();
                }
                examResultView.onSuccess(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<ExamBaseResultBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examResultView.showMsg(error);
            }

            @Override
            public void onFinish() {
                examResultView.freshComplete();
            }
        });
    }
}
