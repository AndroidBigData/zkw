package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.HotCityBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.SearchJobHotCityModel;
import com.zjwam.zkw.mvp.model.imodel.ISearchJobHotCityModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.ISearchJobHotCityPresenter;
import com.zjwam.zkw.mvp.view.ISearchJobHotCityView;
import com.zjwam.zkw.util.Config;

import java.util.List;

public class SearchJobHotCityPresenter implements ISearchJobHotCityPresenter {
    private Context context;
    private ISearchJobHotCityView searchJobHotCityView;
    private ISearchJobHotCityModel searchJobHotCityModel;

    public SearchJobHotCityPresenter(Context context, ISearchJobHotCityView searchJobHotCityView) {
        this.context = context;
        this.searchJobHotCityView = searchJobHotCityView;
        searchJobHotCityModel  = new SearchJobHotCityModel();
    }

    @Override
    public void getHotCity() {
        searchJobHotCityModel.getHotCity(Config.URL + "api/resume/hotCity", context, null, new BasicCallback<ResponseBean<List<HotCityBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<HotCityBean>>> response) {
                searchJobHotCityView.setHotCity(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<HotCityBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                searchJobHotCityView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
