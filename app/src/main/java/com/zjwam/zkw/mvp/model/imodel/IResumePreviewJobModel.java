package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.ResumePreviewJobBean;

import java.util.Map;

public interface IResumePreviewJobModel {
    void getPreviewEdu(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ResumePreviewJobBean>> basicCallback);
}
