package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.JobDetailsBean;
import com.zjwam.zkw.entity.MineJobResumeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.JobDetailsModel;
import com.zjwam.zkw.mvp.model.imodel.IJobDetailsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobDetailsPresenter;
import com.zjwam.zkw.mvp.view.IJobDetailsView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobDeatailsPresenter implements IJobDetailsPresenter {
    private Context context;
    private IJobDetailsView jobDetailsView;
    private IJobDetailsModel jobDetailsModel;
    private Map<String,String> param;

    public JobDeatailsPresenter(Context context, IJobDetailsView jobDetailsView) {
        this.context = context;
        this.jobDetailsView = jobDetailsView;
        jobDetailsModel = new JobDetailsModel();
    }

    @Override
    public void getJobDetails(String id) {
        param = new HashMap<>();
        param.put("id",id);
        param.put("uid",jobDetailsModel.uid(context));
        jobDetailsModel.getJobDetails(Config.URL + "api/resume/jobDetail", context, param, new BasicCallback<ResponseBean<JobDetailsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobDetailsBean>> response) {
                jobDetailsView.refresh();
                jobDetailsView.setJobDetails(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<JobDetailsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {
                jobDetailsView.refreshComplete();
            }
        });
    }

    @Override
    public void holdJob(String jobId, String companyId) {
        param = new HashMap<>();
        param.put("uid",jobDetailsModel.uid(context));
        param.put("id",jobId);
        param.put("company_id",companyId);
        jobDetailsModel.holdJob(Config.URL + "api/resume/hold", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                jobDetailsView.hold();
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {
                jobDetailsView.holdFinish();
            }
        });
    }

    @Override
    public void getResume() {
        param = new HashMap<>();
        param.put("uid",jobDetailsModel.uid(context));
        jobDetailsModel.getResume(Config.URL + "api/user/resume", context, param, new BasicCallback<ResponseBean<List<MineJobResumeBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<MineJobResumeBean>>> response) {
                jobDetailsView.showDialog(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<MineJobResumeBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void sendResume(String rid, String jid, String cid) {
        param = new HashMap<>();
        param.put("uid",jobDetailsModel.uid(context));
        param.put("rid",rid);
        param.put("jid",jid);
        param.put("cid",cid);
        jobDetailsModel.sendResume(Config.URL + "api/user/sendResume", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                jobDetailsView.sendResume();
                jobDetailsView.showMsg(response.body().msg);
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
