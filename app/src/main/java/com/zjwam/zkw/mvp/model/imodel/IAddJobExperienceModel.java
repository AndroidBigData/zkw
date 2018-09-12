package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePickerBean;

import java.util.Map;

public interface IAddJobExperienceModel {
    void getChoiceInfo(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ResumePickerBean>> basicCallback);
    void saveExperience(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
}
