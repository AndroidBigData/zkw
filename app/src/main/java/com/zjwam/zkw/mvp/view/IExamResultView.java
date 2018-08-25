package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ExamBaseResultBean;

public interface IExamResultView {
    void onSuccess(ExamBaseResultBean list);
    void showMsg(String msg);
    void refresh();
    void freshComplete();
    void holdExamTest();
    void holdFinish();
}
