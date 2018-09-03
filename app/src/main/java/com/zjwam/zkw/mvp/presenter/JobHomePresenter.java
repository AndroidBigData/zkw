package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.JobHomeModel;
import com.zjwam.zkw.mvp.model.imodel.IJobHomeModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobHomePresenter;
import com.zjwam.zkw.mvp.view.IJobHomeView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class JobHomePresenter implements IJobHomePresenter {
    private Context context;
    private IJobHomeView jobHomeView;
    private IJobHomeModel jobHomeModel;
    private Map<String,String> param;

    public JobHomePresenter(Context context, IJobHomeView jobHomeView) {
        this.context = context;
        this.jobHomeView = jobHomeView;
        jobHomeModel = new JobHomeModel();
    }

    @Override
    public void getJobItem(String page, final boolean isRefresh) {
        param = new HashMap<>();
        param.put("page",page);
        jobHomeModel.getJobItem(Config.URL + "api/resume/index", context, param, new BasicCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                if (isRefresh){
                    jobHomeView.refresh();
                }
                JobHomeBean jobHomeBean = response.body().data;
                jobHomeView.addJobItem(jobHomeBean.getResume(),jobHomeBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobHomeView.showMsg(error);
            }

            @Override
            public void onFinish() {
                jobHomeView.refreshComplele();
            }
        });
    }
}
