package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.JobDetailsBean;

public interface IMineDeliveryView {
    void setJobDetails(JobDetailsBean jobDetails);
    void showMsg(String msg);
}
