package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePickerBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.AddJobEducationModel;
import com.zjwam.zkw.mvp.model.imodel.IAddJobEducationModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IAddJobEducationPresenter;
import com.zjwam.zkw.mvp.view.IAddJobEducationView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class AddJobEducationPresenter implements IAddJobEducationPresenter {
    private Context context;
    private IAddJobEducationView jobEducationView;
    private IAddJobEducationModel jobEducationModel;
    private Map<String,String> param;

    public AddJobEducationPresenter(Context context, IAddJobEducationView jobEducationView) {
        this.context = context;
        this.jobEducationView = jobEducationView;
        jobEducationModel = new AddJobEducationModel();
    }

    @Override
    public void getEducaion() {
        jobEducationModel.getEducation(Config.URL + "api/resume/setEducation", context, null, new BasicCallback<ResponseBean<ResumePickerBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePickerBean>> response) {
                jobEducationView.getEducation(response.body().data.getEducation());
            }

            @Override
            public void onError(Response<ResponseBean<ResumePickerBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobEducationView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void saveEducation(final int type,String resume_id,String school,String school_nature,String professional,String begin_time,String end_time) {
        param = new HashMap<>();
        param.put("resume_id",resume_id);
        param.put("school",school);
        param.put("school_nature",school_nature);
        param.put("professional",professional);
        param.put("begin_time",begin_time);
        param.put("end_time",end_time);
        jobEducationModel.saveEducation(Config.URL + "api/resume/saveEducation", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (type == 0){
                    jobEducationView.saveEducation();
                }else if (type == 1){
                    jobEducationView.goEducation();
                }

            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobEducationView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
