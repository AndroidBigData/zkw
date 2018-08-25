package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ExamModel;
import com.zjwam.zkw.mvp.model.imodel.IExamModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamPresenter;
import com.zjwam.zkw.mvp.view.IExamView;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;

import java.util.HashMap;
import java.util.Map;

public class ExamPresenter implements IExamPresenter {
    private Context context;
    private IExamView examView;
    private IExamModel examModel;
    private Map<String,String> param;

    public ExamPresenter(Context context, IExamView examView) {
        this.context = context;
        this.examView = examView;
        examModel = new ExamModel();
    }

    @Override
    public void getExam(String page,String name,String id,final boolean isRefresh) {
        param = new HashMap<>();
        if (id.length()>0){
            param.put("id",id);
            param.put("page",page);
            param.put("uid",examModel.uid(context));
            examModel.getFLResult(Config.URL + "api/exam/cate_search_exam", context, param, new BasicCallback<ResponseBean<ExamBean>>() {
                @Override
                public void onSuccess(Response<ResponseBean<ExamBean>> response) {
                    if (isRefresh){
                        examView.refresh();
                    }
                    ExamBean examBean = response.body().data;
                    examView.addExamItem(examBean.getExam(),examBean.getCount());
                }

                @Override
                public void onError(Response<ResponseBean<ExamBean>> response) {
                    Throwable exception = response.getException();
                    String error = HttpErrorMsg.getErrorMsg(exception);
                    examView.showMsg(error);
                    if (!(exception instanceof MyException)){
                        examView.loadMoreError();
                    }
                }

                @Override
                public void onFinish() {
                    examView.refreshComplele();
                }
            });
        }else {
            if (name.length()>0){
                param.put("page",page);
                param.put("name",name);
                param.put("uid",examModel.uid(context));
            }else {
                param.put("page",page);
                param.put("uid",examModel.uid(context));
            }
            examModel.getExam(Config.URL + "api/exam/index", context, param,new BasicCallback<ResponseBean<ExamBean>>() {
                @Override
                public void onSuccess(Response<ResponseBean<ExamBean>> response) {
                    if (isRefresh){
                        examView.refresh();
                    }
                    ExamBean examBean = response.body().data;
                    examView.addExamItem(examBean.getExam(),examBean.getCount());
                }

                @Override
                public void onError(Response<ResponseBean<ExamBean>> response) {
                    Throwable exception = response.getException();
                    String error = HttpErrorMsg.getErrorMsg(exception);
                    examView.showMsg(error);
                    if (!(exception instanceof MyException)){
                        examView.loadMoreError();
                    }
                }

                @Override
                public void onFinish() {
                    examView.refreshComplele();
                }
            });
        }
    }

    @Override
    public void getExamTJ(String wid) {
        examModel.getExamFL(Config.URL + "api/exam/cate_search?wid="+wid, context, new BasicCallback<ClassSearchBean>() {
            @Override
            public void onSuccess(Response<ClassSearchBean> response) {
                examView.getExamFL(response.body());
            }

            @Override
            public void onError(Response<ClassSearchBean> response) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void holdExam(String id) {
        param = new HashMap<>();
        param.put("eid",id);
        param.put("type", String.valueOf(1));
        param.put("uid",examModel.uid(context));
        examModel.holdExam(Config.URL + "api/user/examRunHold", context, param, new BasicCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                examView.holdExam();
                examView.showMsg(response.body().msg);
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                examView.showMsg(error);
            }

            @Override
            public void onFinish() {
                examView.holdFinish();
            }
        });
    }

}
