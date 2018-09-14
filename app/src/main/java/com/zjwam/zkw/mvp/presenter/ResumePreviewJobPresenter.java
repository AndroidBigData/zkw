package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewJobBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ResumePreviewJobModel;
import com.zjwam.zkw.mvp.model.imodel.IResumePreviewJobModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumePreviewPresenter;
import com.zjwam.zkw.mvp.view.IResumePreviewJobView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ResumePreviewJobPresenter implements IResumePreviewPresenter {
    private Context context;
    private IResumePreviewJobView previewEduView;
    private IResumePreviewJobModel previewEduModel;
    private Map<String, String> param;

    public ResumePreviewJobPresenter(Context context, IResumePreviewJobView previewEduView) {
        this.context = context;
        this.previewEduView = previewEduView;
        previewEduModel = new ResumePreviewJobModel();
    }

    @Override
    public void getPreviewData(String id) {
        param = new HashMap<>();
        param.put("id",id);
        previewEduModel.getPreviewEdu(Config.URL + "api/resume/editorEmployment", context, param, new BasicCallback<ResponseBean<ResumePreviewJobBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePreviewJobBean>> response) {
                previewEduView.setPreview(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePreviewJobBean>> response) {
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
