package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumeDetailsBean;

import java.util.Map;

public interface IResumeDetailsModel {
    void getDetails(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ResumeDetailsBean>> basicCallback);
    String uid(Context context);
}
