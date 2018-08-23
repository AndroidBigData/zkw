package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IExamRecordPresenter {
    void getRecodItem(String page,boolean isRefresh);
    void deleteRecord(String id);
}
