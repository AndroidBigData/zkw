package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SearchClassBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ClassNewsMoreModel;
import com.zjwam.zkw.mvp.model.imodel.IClassNewsMoreModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IClassNewsMorePresenter;
import com.zjwam.zkw.mvp.view.IClassNewsMoreView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class ClassNewsMorePresenter implements IClassNewsMorePresenter {
    private Context context;
    private IClassNewsMoreView classNewsMoreView;
    private IClassNewsMoreModel classNewsMoreModel;
    private Map<String,String> param;

    public ClassNewsMorePresenter(Context context, IClassNewsMoreView classNewsMoreView) {
        this.context = context;
        this.classNewsMoreView = classNewsMoreView;
        classNewsMoreModel = new ClassNewsMoreModel();
    }

    @Override
    public void getNews(String cid, String page, final boolean isRefresh) {
        param = new HashMap<>();
        param.put("cid",cid);
        param.put("page",page);
        classNewsMoreModel.getNews(Config.URL + "api/news/class_lists", context, param, new BasicCallback<ResponseBean<SearchClassBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<SearchClassBean>> response) {
                if (isRefresh){
                    classNewsMoreView.refresh();
                }
                SearchClassBean searchClassBean = response.body().data;
                classNewsMoreView.getNews(searchClassBean.getClass_list(),searchClassBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<SearchClassBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                classNewsMoreView.showMsg(error);
            }

            @Override
            public void onFinish() {
                classNewsMoreView.refreshComplele();
            }
        });
    }
}
