package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumeDetailsBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ResumeDetailsModel;
import com.zjwam.zkw.mvp.model.imodel.IResumeDetailsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumeDetailsPresenter;
import com.zjwam.zkw.mvp.view.IResumeDetailsView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ReumeDetailsPresenter implements IResumeDetailsPresenter{
    private Context context;
    private IResumeDetailsView resumeDetailsView;
    private IResumeDetailsModel resumeDetailsModel;
    private Map<String,String > param;

    public ReumeDetailsPresenter(Context context, IResumeDetailsView resumeDetailsView) {
        this.context = context;
        this.resumeDetailsView = resumeDetailsView;
        resumeDetailsModel = new ResumeDetailsModel();
    }

    @Override
    public void getDetails(String id) {
        param = new HashMap<>();
        param.put("uid",resumeDetailsModel.uid(context));
        param.put("id",id);
        resumeDetailsModel.getDetails(Config.URL + "api/resume/resumeDetail", context, param, new BasicCallback<ResponseBean<ResumeDetailsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumeDetailsBean>> response) {
                resumeDetailsView.setDetails(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<ResumeDetailsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                resumeDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
