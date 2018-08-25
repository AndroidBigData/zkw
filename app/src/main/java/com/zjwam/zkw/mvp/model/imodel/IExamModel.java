package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IExamModel {
    void getExam(String url, Object context,Map<String,String> param, BasicCallback<ResponseBean<ExamBean>> basicCallback);
    void getExamFL(String url, Object context, BasicCallback<ClassSearchBean> basicCallback);
    void getFLResult(String url, Object context,Map<String,String> param, BasicCallback<ResponseBean<ExamBean>> basicCallback);
    void holdExam(String url, Object context,Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
    String uid(Context context);
}
