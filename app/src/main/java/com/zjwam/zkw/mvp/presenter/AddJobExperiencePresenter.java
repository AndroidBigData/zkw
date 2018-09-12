package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePickerBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.AddJobExperienceModel;
import com.zjwam.zkw.mvp.model.imodel.IAddJobExperienceModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IAddJobExpericencePresenter;
import com.zjwam.zkw.mvp.view.IAddJobExperienceView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class AddJobExperiencePresenter implements IAddJobExpericencePresenter {
    private Context context;
    private IAddJobExperienceView experienceView;
    private IAddJobExperienceModel experienceModel;
    private Map<String,String> param;

    public AddJobExperiencePresenter(Context context, IAddJobExperienceView experienceView) {
        this.context = context;
        this.experienceView = experienceView;
        experienceModel = new AddJobExperienceModel();
    }

    @Override
    public void getChoiceInfo() {
        experienceModel.getChoiceInfo(Config.URL + "api/resume/setNature", context, null, new BasicCallback<ResponseBean<ResumePickerBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePickerBean>> response) {
                experienceView.getChoiceInfo(response.body().data.getNature());
            }

            @Override
            public void onError(Response<ResponseBean<ResumePickerBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                experienceView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void saveExpericence(final int type, String resume_id, String work_company, String begin_time, String end_time, String nature, String work_job, String high_money, String content) {
        param = new HashMap<>();
        param.put("resume_id",resume_id);
        param.put("work_company",work_company);
        param.put("begin_time",begin_time);
        param.put("end_time",end_time);
        param.put("nature",nature);
        param.put("work_job",work_job);
        param.put("high_money",high_money);
        param.put("content",content);
        experienceModel.saveExperience(Config.URL + "api/resume/saveEmployment", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (type == 0){
                    experienceView.saveExperienceAgain();
                }else if (type == 1){
                    experienceView.saveExperienceMore();
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                experienceView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
