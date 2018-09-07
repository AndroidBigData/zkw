package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.SearchJobDetailsModel;
import com.zjwam.zkw.mvp.model.imodel.ISearchJobDetailsModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.ISearchJobDetailsPresenter;
import com.zjwam.zkw.mvp.view.ISearchJobDetailsView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class SearchJobDetailsPresenter implements ISearchJobDetailsPresenter {
    private Context context;
    private ISearchJobDetailsView searchJobDetailsView;
    private ISearchJobDetailsModel searchJobDetailsModel;
    private Map<String,String> param;

    public SearchJobDetailsPresenter(Context context, ISearchJobDetailsView searchJobDetailsView) {
        this.context = context;
        this.searchJobDetailsView = searchJobDetailsView;
        searchJobDetailsModel = new SearchJobDetailsModel();
    }

    @Override
    public void getSearchPop(String city) {
        param = new HashMap<>();
        param.put("city",city);
        searchJobDetailsModel.getSearchPop(Config.URL + "api/resume/setCondition", context, param, new BasicCallback<ResponseBean<SearchJobDetailsPopBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<SearchJobDetailsPopBean>> response) {
                SearchJobDetailsPopBean searchJobDetailsPopBean = response.body().data;
                searchJobDetailsView.setSearch(searchJobDetailsPopBean.getArea(),searchJobDetailsPopBean.getEducation(),
                        searchJobDetailsPopBean.getWorkyear(),searchJobDetailsPopBean.getMoney());
            }

            @Override
            public void onError(Response<ResponseBean<SearchJobDetailsPopBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                searchJobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void getSearchJob(String name, String city, String strcate, String strjob, String area, String money, String education, String workyear, final boolean isRefresh, String page) {
        param = new HashMap<>();
        param.put("name",name);
        param.put("city",city);
        param.put("strcate",strcate);
        param.put("strjob",strjob);
        param.put("area",area);
        param.put("money",money);
        param.put("education",education);
        param.put("workyear",workyear);
        param.put("page",page);
        searchJobDetailsModel.getSearchJob(Config.URL + "api/resume/setJob", context, param, new BasicCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                if (isRefresh){
                    searchJobDetailsView.refresh();
                }
                JobHomeBean jobHomeBean = response.body().data;
                searchJobDetailsView.setSearchJob(jobHomeBean.getResume(),jobHomeBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                searchJobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {
                searchJobDetailsView.freshComplete();
            }
        });
    }

    @Override
    public void getSearchJobText(String name, String city, String area, String money, String education, String workyear, final boolean isRefresh, String page) {
        param = new HashMap<>();
        param.put("name",name);
        param.put("city",city);
        param.put("area",area);
        param.put("money",money);
        param.put("education",education);
        param.put("workyear",workyear);
        param.put("page",page);
        searchJobDetailsModel.getSearchJobText(Config.URL + "api/resume/setJob2", context, param, new BasicCallback<ResponseBean<JobHomeBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JobHomeBean>> response) {
                if (isRefresh){
                    searchJobDetailsView.refresh();
                }
                JobHomeBean jobHomeBean = response.body().data;
                searchJobDetailsView.setSearchJob(jobHomeBean.getResume(),jobHomeBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<JobHomeBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                searchJobDetailsView.showMsg(error);
            }

            @Override
            public void onFinish() {
                searchJobDetailsView.freshComplete();
            }
        });
    }
}
