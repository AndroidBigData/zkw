package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.HotCityBean;

import java.util.List;

public interface ISearchJobHotCityView {
    void setHotCity(List<HotCityBean> list);
    void showMsg(String msg);
}
