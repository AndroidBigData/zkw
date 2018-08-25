package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ExamTestCollectionBean;
import java.util.List;

public interface IExamTestCollectionView {
    void onSuccess(List<ExamTestCollectionBean.Paper> list, int count);
    void showMsg(String msg);
    void refresh();
    void freshComplete();
}
