package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.CollectionExamBean;

import java.util.List;

public interface IExamCollectionView {
    void onSuccess(List<CollectionExamBean.Hold> list,int count);
    void showMsg(String msg);
    void refresh();
    void freshComplete();
}
