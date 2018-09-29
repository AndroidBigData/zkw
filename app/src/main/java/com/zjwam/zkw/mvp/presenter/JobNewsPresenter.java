package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.JobNewsBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.JobNewsModel;
import com.zjwam.zkw.mvp.model.imodel.IJobNewsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobNewsPresenter;
import com.zjwam.zkw.mvp.view.IJobNewsView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class JobNewsPresenter implements IJobNewsPresenter {
    private Context context;
    private IJobNewsView jobNewsView;
    private IJobNewsModel jobNewsModel;
    private Map<String,String> param;

    public JobNewsPresenter(Context context, IJobNewsView jobNewsView) {
        this.context = context;
        this.jobNewsView = jobNewsView;
        jobNewsModel = new JobNewsModel();
    }

    @Override
    public void getNews(String city) {
        param = new HashMap<>();
        param.put("city",city);
        jobNewsModel.getNews(Config.URL + "api/news/job", context, param, new BasicCallback<ResponseBean<JobNewsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobNewsBean>> response) {
                jobNewsView.refresh();
                JobNewsBean jobNewsBean = response.body().data;
//                List<String> img = new ArrayList<>();
//                for (NewsBean item : jobNewsBean.getCate21()) {
//                    img.add(item.getTitle_img());
//                }
                jobNewsView.setNews(jobNewsBean.getCate13(),jobNewsBean.getCate14(),jobNewsBean.getCate15(),null);
            }

            @Override
            public void onError(Response<ResponseBean<JobNewsBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                jobNewsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
