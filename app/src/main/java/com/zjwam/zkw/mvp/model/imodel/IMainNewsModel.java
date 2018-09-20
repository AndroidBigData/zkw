package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.MainNewsBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IMainNewsModel {
    void getMainNews(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<MainNewsBean>> basicCallback);
}
