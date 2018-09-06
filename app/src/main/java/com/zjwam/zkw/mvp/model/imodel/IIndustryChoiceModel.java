package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.IndustryChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.List;
import java.util.Map;

public interface IIndustryChoiceModel {
    void getIndustry(String url, Object context, Map<String,String> param,BasicCallback<ResponseBean<List<IndustryChoiceBean>>> basicCallback);
}
