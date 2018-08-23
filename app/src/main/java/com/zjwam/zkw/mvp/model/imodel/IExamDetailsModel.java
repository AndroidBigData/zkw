package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamDetailsBean;
import com.zjwam.zkw.entity.ExamUpBean;
import com.zjwam.zkw.entity.ResponseBean;


import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IExamDetailsModel {
    void getExamDetails(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<List<ExamDetailsBean>>> basicCallback);
    void upaExam(String url, Object context, Map<String,String> param, JSONObject json, BasicCallback<ResponseBean<ExamUpBean>> basicCallback);
}
