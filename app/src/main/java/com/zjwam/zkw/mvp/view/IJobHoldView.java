package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.JobHomeBean;

import java.util.List;

public interface IJobHoldView {
    void setHoldItem(List<JobHomeBean.Resume> data, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
    void cancelHold();
}
