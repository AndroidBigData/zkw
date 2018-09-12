package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResumePickerBean;

import java.util.List;

public interface ICreateResumeView {
    void getResumeChoiceData(List<ResumePickerBean.BasicInfo> job_type,List<ResumePickerBean.BasicInfo> hiredate);
    void showMsg(String msg);
    void setProfession(List<ProfessionChoiceBean> list);
    void saveResume(long id);
}
