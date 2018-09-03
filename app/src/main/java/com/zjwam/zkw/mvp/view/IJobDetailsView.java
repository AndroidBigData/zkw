package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.JobDetailsBean;

public interface IJobDetailsView {
    void setJobDetails(JobDetailsBean jobDetails);
    void showMsg(String msg);
    void refresh();
    void refreshComplete();
    void hold();
    void holdFinish();
}
