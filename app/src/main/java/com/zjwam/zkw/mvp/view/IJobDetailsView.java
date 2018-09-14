package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.JobDetailsBean;
import com.zjwam.zkw.entity.MineJobResumeBean;

import java.util.List;

public interface IJobDetailsView {
    void setJobDetails(JobDetailsBean jobDetails);
    void showMsg(String msg);
    void refresh();
    void refreshComplete();
    void hold();
    void holdFinish();
    void showDialog(List<MineJobResumeBean> data);
    void sendResume();
}
