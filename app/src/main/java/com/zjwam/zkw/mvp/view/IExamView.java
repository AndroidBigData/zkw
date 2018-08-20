package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.ExamBean;

import java.util.List;

public interface IExamView {
    void addExamItem(List<ExamBean.Exam> exam,int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
    void loadMoreError();
    void getExamFL(ClassSearchBean classSearchBean);
}
