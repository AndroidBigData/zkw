package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.TeacherMoreBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.TeacherMoreModel;
import com.zjwam.zkw.mvp.model.imodel.ITeacherMoreModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.ITeacherMorePresenter;
import com.zjwam.zkw.mvp.view.ITeacherMoreView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class TeacherMorePresenter implements ITeacherMorePresenter {
    private Context context;
    private ITeacherMoreView teacherMoreView;
    private ITeacherMoreModel teacherMoreModel;
    private Map<String,String> param;

    public TeacherMorePresenter(Context context, ITeacherMoreView teacherMoreView) {
        this.context = context;
        this.teacherMoreView = teacherMoreView;
        teacherMoreModel = new TeacherMoreModel();
    }

    @Override
    public void getInfo(String id) {
        param = new HashMap<>();
        param.put("id",id);
        teacherMoreModel.getInfo(Config.URL + "api/news/teacher", context, param, new BasicCallback<ResponseBean<TeacherMoreBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<TeacherMoreBean>> response) {
                TeacherMoreBean teacherMoreBean = response.body().data;
                teacherMoreView.setInfo(teacherMoreBean.getTeacher(),teacherMoreBean.getClassfor());
            }

            @Override
            public void onError(Response<ResponseBean<TeacherMoreBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                teacherMoreView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
