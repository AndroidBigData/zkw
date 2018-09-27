package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.BusinessNewsBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IBusinessNewsModel {
    void getNews(String url, Object context, BasicCallback<ResponseBean<BusinessNewsBean>> basicCallback);
}
