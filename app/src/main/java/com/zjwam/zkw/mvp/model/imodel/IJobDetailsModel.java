package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.JobDetailsBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IJobDetailsModel {
    void getJobDetails(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<JobDetailsBean>> basicCallback);
    String uid(Context context);
    void holdJob(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
}
