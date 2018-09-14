package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineJobResumeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.MineJobResumeModel;
import com.zjwam.zkw.mvp.model.imodel.IMineJobResumeModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IMineJobResumePresenter;
import com.zjwam.zkw.mvp.view.IMineJobResumeView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineJobResumePresenter implements IMineJobResumePresenter {
    private Context context;
    private IMineJobResumeView mineJobResumeView;
    private IMineJobResumeModel mineJobResumeModel;
    private Map<String,String> param;

    public MineJobResumePresenter(Context context, IMineJobResumeView mineJobResumeView) {
        this.context = context;
        this.mineJobResumeView = mineJobResumeView;
        mineJobResumeModel = new MineJobResumeModel();
    }

    @Override
    public void getJobResume() {
        param = new HashMap<>();
        param.put("uid",mineJobResumeModel.uid(context));
        mineJobResumeModel.getJobResume(Config.URL + "api/user/resume", context, param, new BasicCallback<ResponseBean<List<MineJobResumeBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<MineJobResumeBean>>> response) {
                mineJobResumeView.refresh();
                mineJobResumeView.setResume(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<MineJobResumeBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                mineJobResumeView.showMsg(error);
            }

            @Override
            public void onFinish() {
                mineJobResumeView.refreshComplete();
            }
        });
    }

    @Override
    public void deleteResume(String id) {
        param = new HashMap<>();
        param.put("id",id);
        mineJobResumeModel.deleteResume(Config.URL + "api/resume/resumeDel", context, param, new BasicCallback<ResponseBean<List<EmptyBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<EmptyBean>>> response){
                mineJobResumeView.deleteResume();
            }

            @Override
            public void onError(Response<ResponseBean<List<EmptyBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                mineJobResumeView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
