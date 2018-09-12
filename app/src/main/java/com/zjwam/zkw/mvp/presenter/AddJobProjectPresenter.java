package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.AddJobProjectModel;
import com.zjwam.zkw.mvp.model.imodel.IAddJobProjectModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IAddJobProjectPresenter;
import com.zjwam.zkw.mvp.view.IAddJobProjectView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class AddJobProjectPresenter implements IAddJobProjectPresenter {
    private Context context;
    private IAddJobProjectView projectView;
    private IAddJobProjectModel projectModel;
    private Map<String,String> param;

    public AddJobProjectPresenter(Context context, IAddJobProjectView projectView) {
        this.context = context;
        this.projectView = projectView;
        projectModel = new AddJobProjectModel();
    }

    @Override
    public void saveProject(final int type, String resume_id, String project_name, String begin_time, String end_time, String intro, String content) {
        param = new HashMap<>();
        param.put("resume_id",resume_id);
        param.put("project_name",project_name);
        param.put("begin_time",begin_time);
        param.put("end_time",end_time);
        param.put("intro",intro);
        param.put("content",content);
        projectModel.saveProject(Config.URL + "api/resume/saveProject", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (type == 0){
                    projectView.saveProjectGo();
                }else if (type == 1){
                    projectView.saveProjectMore();
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                projectView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
