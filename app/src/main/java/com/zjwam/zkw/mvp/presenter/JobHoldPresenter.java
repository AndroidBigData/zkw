package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.JobHoldModel;
import com.zjwam.zkw.mvp.model.imodel.IJobHoldModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobHoldPresenter;
import com.zjwam.zkw.mvp.view.IJobHoldView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class JobHoldPresenter implements IJobHoldPresenter {
    private Context context;
    private IJobHoldView jobHoldView;
    private IJobHoldModel jobHoldModel;
    private Map<String,String> param;

    public JobHoldPresenter(Context context, IJobHoldView jobHoldView) {
        this.context = context;
        this.jobHoldView = jobHoldView;
        jobHoldModel = new JobHoldModel();
    }

    @Override
    public void getHold(final boolean isRefresh, String page) {
        param = new HashMap<>();
        param.put("page",page);
        param.put("uid",jobHoldModel.uid(context));
        jobHoldModel.getHold(Config.URL + "api/resume/hold_list", context, param, new BasicCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                if (isRefresh){
                    jobHoldView.refresh();
                }
                JobHomeBean jobHomeBean = response.body().data;
                jobHoldView.setHoldItem(jobHomeBean.getResume(),jobHomeBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobHoldView.showMsg(error);
            }

            @Override
            public void onFinish() {
                jobHoldView.refreshComplele();
            }
        });
    }

    @Override
    public void cancelHold(String hold_id) {
        param = new HashMap<>();
        param.put("uid",jobHoldModel.uid(context));
        param.put("hold_id",hold_id);
        jobHoldModel.cancelHold(Config.URL + "api/resume/hold_del", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                jobHoldView.cancelHold();
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobHoldView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
