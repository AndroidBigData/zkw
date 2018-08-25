package com.zjwam.zkw.mvp.model.imodel;

import android.content.Context;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.CollectionExamBean;
import com.zjwam.zkw.entity.ResponseBean;

import java.util.Map;

public interface IExamCollectionModel {
    void getCollrctionItem(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<CollectionExamBean>> basicCallback);
    String uid(Context context);
}
