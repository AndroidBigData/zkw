package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.CurriculumSelectBean;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePickerBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.CreateResumeModel;
import com.zjwam.zkw.mvp.model.imodel.ICreateResumeModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.ICreateResumePresenter;
import com.zjwam.zkw.mvp.view.ICreateResumeView;
import com.zjwam.zkw.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateResumePresenter implements ICreateResumePresenter {
    private Context context;
    private ICreateResumeView createResumeView;
    private ICreateResumeModel createResumeModel;
    private Map<String,String> param;

    public CreateResumePresenter(Context context, ICreateResumeView createResumeView) {
        this.context = context;
        this.createResumeView = createResumeView;
        createResumeModel = new CreateResumeModel();
    }

    @Override
    public void getResumeChoiceInfo() {
        createResumeModel.getRexumeChoiceInfo(Config.URL + "api/resume/searchCond", context, null, new BasicCallback<ResponseBean<ResumePickerBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePickerBean>> response) {
                ResumePickerBean resumePickerBean = response.body().data;
                createResumeView.getResumeChoiceData(resumePickerBean.getJob_type(),resumePickerBean.getHiredate());
            }

            @Override
            public void onError(Response<ResponseBean<ResumePickerBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                createResumeView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void getProfession() {
        createResumeModel.getProfession(Config.URL + "api/resume/searchJob", context, null, new BasicCallback<ResponseBean<List<ProfessionChoiceBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ProfessionChoiceBean>>> response) {
                createResumeView.setProfession(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<ProfessionChoiceBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                createResumeView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void saveResume(String title, String job_type, String industry_id, String hiredate, String low_money,
                           String high_money, String name, String gender, String age, String phone, String evaluate, String skill) {
        param = new HashMap<>();
        param.put("uid",createResumeModel.uid(context));
        param.put("title",title);
        param.put("job_type",job_type);
        param.put("industry_id",industry_id);
        param.put("hiredate",hiredate);
        param.put("low_money",low_money);
        param.put("high_money",high_money);
        param.put("name",name);
        param.put("gender",gender);
        param.put("age",age);
        param.put("phone",phone);
        param.put("evaluate",evaluate);
        param.put("skill",skill);
        createResumeModel.saveResume(Config.URL + "api/resume/saveResume", context, param ,new BasicCallback<ResponseBean<CurriculumSelectBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<CurriculumSelectBean>> response) {
                createResumeView.saveResume(response.body().data.getId());
            }

            @Override
            public void onError(Response<ResponseBean<CurriculumSelectBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                createResumeView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
