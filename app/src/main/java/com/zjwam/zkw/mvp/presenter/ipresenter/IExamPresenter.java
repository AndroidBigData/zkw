package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IExamPresenter{
    void getExam(String page,String name,String id,boolean isRefresh);
    void getExamTJ(String wid);
    void holdExam(String id);
}
