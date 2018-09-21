package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ClassNewsBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IClassNewsModel {
    void getNews(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ClassNewsBean>> basicCallback);
}
