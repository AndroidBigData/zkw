package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IJobHoldModel {
    void getHold(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<JobHomeBean>> basicCallback);
    String uid(Context context);
    void cancelHold(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
}
