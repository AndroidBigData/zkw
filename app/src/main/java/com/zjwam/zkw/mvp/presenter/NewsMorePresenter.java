package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.NewsMoreBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.NewsMoreModel;
import com.zjwam.zkw.mvp.model.imodel.INewsMoreModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.INewsMorePresenter;
import com.zjwam.zkw.mvp.view.INewsMoreView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class NewsMorePresenter implements INewsMorePresenter {
    private Context context;
    private INewsMoreView newsMoreView;
    private INewsMoreModel newsMoreModel;
    private Map<String,String> param;

    public NewsMorePresenter(Context context, INewsMoreView newsMoreView) {
        this.context = context;
        this.newsMoreView = newsMoreView;
        newsMoreModel = new NewsMoreModel();
    }

    @Override
    public void getNews(String id, String page,String city ,final boolean isRefresh) {
        param = new HashMap<>();
        param.put("cid",id);
        param.put("page",page);
        param.put("city",city);
        newsMoreModel.getNews(Config.URL + "api/news/news_lists", context, param, new BasicCallback<ResponseBean<NewsMoreBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<NewsMoreBean>> response) {
                if (isRefresh){
                    newsMoreView.refresh();
                }
                NewsMoreBean newsMoreBean = response.body().data;
                newsMoreView.getNews(newsMoreBean.getNews(),newsMoreBean.getCount());
            }

            @Override
            public void onError(Response<ResponseBean<NewsMoreBean>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                newsMoreView.showMsg(error);
            }

            @Override
            public void onFinish() {
                newsMoreView.refreshComplele();
            }
        });
    }
}
