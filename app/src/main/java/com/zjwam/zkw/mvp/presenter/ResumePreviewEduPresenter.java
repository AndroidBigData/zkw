package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewEduBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ResumePreviewEduModel;
import com.zjwam.zkw.mvp.model.imodel.IResumePreviewEduModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumePreviewPresenter;
import com.zjwam.zkw.mvp.view.IResumePreviewEduView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ResumePreviewEduPresenter implements IResumePreviewPresenter {
    private Context context;
    private IResumePreviewEduView previewEduView;
    private IResumePreviewEduModel previewEduModel;
    private Map<String, String> param;

    public ResumePreviewEduPresenter(Context context, IResumePreviewEduView previewEduView) {
        this.context = context;
        this.previewEduView = previewEduView;
        previewEduModel = new ResumePreviewEduModel();
    }

    @Override
    public void getPreviewData(String id) {
        param = new HashMap<>();
        param.put("id",id);
        previewEduModel.getPreviewEdu(Config.URL + "api/resume/editorEducation", context, param, new BasicCallback<ResponseBean<ResumePreviewEduBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePreviewEduBean>> response) {
                previewEduView.setPreview(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePreviewEduBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                previewEduView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
