package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ExamDetailsBean;

import java.util.List;

public interface IExamDetailsView {
    void setExam(List<ExamDetailsBean> list);
    void showMsg(String msg);
    void jump2Details(String resultId);
}
