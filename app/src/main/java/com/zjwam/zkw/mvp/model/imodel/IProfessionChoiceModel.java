package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.List;
import java.util.Map;

public interface IProfessionChoiceModel {
    void getProfession(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<ProfessionChoiceBean>>> basicCallback);
}
