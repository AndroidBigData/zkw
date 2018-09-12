package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePickerBean;

import java.util.Map;

public interface IAddJobEducationModel {
    void getEducation(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ResumePickerBean>> basicCallback);
    void saveEducation(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
}
