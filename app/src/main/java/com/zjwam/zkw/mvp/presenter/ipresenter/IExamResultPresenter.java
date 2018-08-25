package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IExamResultPresenter {
    void getExamResult(String id,String eid,boolean isRefresh);
    void holdExamTest(String id,String eid);
}
