package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.HotCityBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.List;
import java.util.Map;

public interface ISearchJobHotCityModel {
    void getHotCity(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<HotCityBean>>> basicCallback);
}
