package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.MineJobResumeBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.List;
import java.util.Map;

public interface IMineJobResumeModel {
    void getJobResume(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<MineJobResumeBean>>> basicCallback);
    String uid(Context context);
}
