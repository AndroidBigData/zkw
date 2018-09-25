package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SearchClassBean;

import java.util.Map;

public interface IClassNewsMoreModel {
    void getNews(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<SearchClassBean>> basicCallback);
}
