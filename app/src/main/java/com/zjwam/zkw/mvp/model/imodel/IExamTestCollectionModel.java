package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ExamTestCollectionBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IExamTestCollectionModel {
    void getTestCollrctionItem(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<ExamTestCollectionBean>> basicCallback);
    String uid(Context context);
}
