package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamDetailsBean;
import com.zjwam.zkw.entity.ExamUpBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ExamDetailsModel;
import com.zjwam.zkw.mvp.model.imodel.IExamDetailsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamDetailsPresenter;
import com.zjwam.zkw.mvp.view.IExamDetailsView;
import com.zjwam.zkw.util.Config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamDetailsPresenter implements IExamDetailsPresenter {
    private Context context;
    private IExamDetailsView examDetailsView;
    private IExamDetailsModel examDetailsModel;
    private Map<String,String> param;

    public ExamDetailsPresenter(Context context, IExamDetailsView examDetailsView) {
        this.context = context;
        this.examDetailsView = examDetailsView;
        examDetailsModel = new ExamDetailsModel();
    }

    @Override
    public void getExamDetails(String id) {
        param = new HashMap<>();
        param.put("id",id);
        examDetailsModel.getExamDetails(Config.URL + "api/exam/getQuestion", context, param, new BasicCallback<ResponseBean<List<ExamDetailsBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ExamDetailsBean>>> response) {
                if (response.body().data.size()>0){
                    examDetailsView.setExam(response.body().data);
                }else {
                    examDetailsView.showMsg("试题内容为空！");
                }
            }

            @Override
            public void onError(Response<ResponseBean<List<ExamDetailsBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void upExam(String id,JSONObject json) {
        examDetailsModel.upaExam(Config.URL + "api/exam/answer", context, null, json,new BasicCallback<ResponseBean<ExamUpBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ExamUpBean>> response) {
                examDetailsView.jump2Details(response.body().data.getId());
            }

            @Override
            public void onError(Response<ResponseBean<ExamUpBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
