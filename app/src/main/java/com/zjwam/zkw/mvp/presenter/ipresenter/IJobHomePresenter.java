package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IJobHomePresenter {
    void getJobItem(String page,String city,boolean isRefresh);
    void getHotCity();
}
