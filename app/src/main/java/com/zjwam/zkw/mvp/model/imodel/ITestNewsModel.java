package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ClassTypeInfo;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.TestNewsBean;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;

import java.util.List;
import java.util.Map;

public interface ITestNewsModel {
    void getInfo(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<ClassTypeInfo>>> basicCallback);
    void queryResult(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<TestQueryResultDialogBean>>> basicCallback);
    void getNews(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<TestNewsBean>> basicCallback);
}
