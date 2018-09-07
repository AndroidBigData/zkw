package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;

import java.util.Map;

public interface ISearchJobDetailsModel {
    void getSearchPop(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<SearchJobDetailsPopBean>> basicCallback);
    void getSearchJob(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<JobHomeBean>> basicCallback);
    void getSearchJobText(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<JobHomeBean>> basicCallback);
}
