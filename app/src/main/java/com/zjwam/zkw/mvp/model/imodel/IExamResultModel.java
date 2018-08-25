package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamBaseResultBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IExamResultModel {
    void getExamResult(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ExamBaseResultBean>> basicCallback);
    String uid(Context context);
    void getExamTestHold(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
}
