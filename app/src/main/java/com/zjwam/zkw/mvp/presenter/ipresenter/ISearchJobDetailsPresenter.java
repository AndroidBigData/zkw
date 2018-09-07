package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface ISearchJobDetailsPresenter {
    void getSearchPop(String city);
    void getSearchJob(String name,String city,String strcate,String strjob,String area,String money,String education,String workyear,boolean isRefresh,String page);
    void getSearchJobText(String name,String city,String area,String money,String education,String workyear,boolean isRefresh,String page);
}
