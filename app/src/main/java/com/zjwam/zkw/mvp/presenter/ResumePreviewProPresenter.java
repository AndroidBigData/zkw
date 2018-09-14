package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewProBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ResumePreviewProModel;
import com.zjwam.zkw.mvp.model.imodel.IResumePreviewProModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumePreviewPresenter;
import com.zjwam.zkw.mvp.view.IResumePreviewProView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ResumePreviewProPresenter implements IResumePreviewPresenter {
    private Context context;
    private IResumePreviewProView previewEduView;
    private IResumePreviewProModel previewEduModel;
    private Map<String, String> param;

    public ResumePreviewProPresenter(Context context, IResumePreviewProView previewEduView) {
        this.context = context;
        this.previewEduView = previewEduView;
        previewEduModel = new ResumePreviewProModel();
    }

    @Override
    public void getPreviewData(String id) {
        param = new HashMap<>();
        param.put("id",id);
        previewEduModel.getPreviewEdu(Config.URL + "api/resume/editorProject", context, param, new BasicCallback<ResponseBean<ResumePreviewProBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<ResumePreviewProBean>> response) {
                previewEduView.setPreview(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<ResumePreviewProBean>> response) {
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
