package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.JobEmplomentBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.JobEmploymentModel;
import com.zjwam.zkw.mvp.model.imodel.IJobEmploymentModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IJobEmplomentPresenter;
import com.zjwam.zkw.mvp.view.IJobEmploymentView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class JobEmplomentPresenter implements IJobEmplomentPresenter {
    private Context context;
    private IJobEmploymentView employmentView;
    private IJobEmploymentModel employmentModel;
    private Map<String , String> param;

    public JobEmplomentPresenter(Context context, IJobEmploymentView employmentView) {
        this.context = context;
        this.employmentView = employmentView;
        employmentModel = new JobEmploymentModel();
    }

    @Override
    public void getEmploment(final boolean isRefresh,String page) {
        param = new HashMap<>();
        param.put("page",page);
        employmentModel.getEmployment(Config.URL + "api/guide/lists", context, param, new BasicCallback<ResponseBean<JobEmplomentBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobEmplomentBean>> response) {
                if (isRefresh){
                    employmentView.refresh();
                }
                JobEmplomentBean jobEmplomentBean = response.body().data;
                employmentView.addEmploymentItem(jobEmplomentBean.getGuide(),jobEmplomentBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<JobEmplomentBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                employmentView.showMsg(error);
            }

            @Override
            public void onFinish() {
                employmentView.refreshComplele();
            }
        });
    }
}
