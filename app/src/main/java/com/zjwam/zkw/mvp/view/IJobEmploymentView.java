package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.JobEmplomentBean;

import java.util.List;

public interface IJobEmploymentView {
    void addEmploymentItem(List<JobEmplomentBean.Guide> data, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
}
