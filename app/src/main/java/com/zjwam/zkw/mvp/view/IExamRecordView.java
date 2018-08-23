package com.zjwam.zkw.mvp.view;



import com.zjwam.zkw.entity.ExamRecordBean;

import java.util.List;

public interface IExamRecordView {
    void addRecordItem(List<ExamRecordBean.Exam> exam, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
    void deleteRecord();
}
