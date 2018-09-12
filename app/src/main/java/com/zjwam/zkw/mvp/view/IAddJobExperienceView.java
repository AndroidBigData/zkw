package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ResumePickerBean;

import java.util.List;

public interface IAddJobExperienceView {
    void getChoiceInfo(List<ResumePickerBean.BasicInfo> list);
    void showMsg(String msg);
    void saveExperienceAgain();
    void saveExperienceMore();
}
