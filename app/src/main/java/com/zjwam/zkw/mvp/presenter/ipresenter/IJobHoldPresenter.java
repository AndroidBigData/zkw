package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IJobHoldPresenter {
    void getHold(boolean isRefresh,String page);
    void cancelHold(String hold_id);
}
