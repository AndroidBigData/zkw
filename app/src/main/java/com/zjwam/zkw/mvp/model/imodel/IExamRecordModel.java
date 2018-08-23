package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ExamRecordBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IExamRecordModel {
    void getRecordItem(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ExamRecordBean>> basicCallback);
    String uid(Context context);
    void deleteRecord(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<EmptyBean>> basicCallback);
}
