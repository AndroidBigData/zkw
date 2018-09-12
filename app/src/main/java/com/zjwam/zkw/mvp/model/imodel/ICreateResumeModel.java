package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumeIdBean;
import com.zjwam.zkw.entity.ResumePickerBean;

import java.util.List;
import java.util.Map;

public interface ICreateResumeModel {
    void getRexumeChoiceInfo(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ResumePickerBean>> basicCallback);
    void getProfession(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<ProfessionChoiceBean>>> basicCallback);
    void saveResume(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ResumeIdBean>> basicCallback);
    String uid(Context context);
}
