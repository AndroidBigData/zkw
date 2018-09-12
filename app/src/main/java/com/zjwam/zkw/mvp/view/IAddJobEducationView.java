package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ResumePickerBean;

import java.util.List;

public interface IAddJobEducationView {
    void getEducation(List<ResumePickerBean.BasicInfo> list);
    void showMsg(String msg);
    void saveEducation();
    void goEducation();
}
