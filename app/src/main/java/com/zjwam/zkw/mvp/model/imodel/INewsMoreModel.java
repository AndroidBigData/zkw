package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.NewsMoreBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface INewsMoreModel {
    void getNews(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<NewsMoreBean>> basicCallback);
}
