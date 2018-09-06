package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.HotCity;
import com.zjwam.zkw.entity.HotCityBean;
import com.zjwam.zkw.entity.JobHomeBean;

import java.util.List;

public interface IJobHomeView {
    void addJobItem(List<JobHomeBean.Resume> list, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
    void setHotCity(List<HotCityBean> list);
}
