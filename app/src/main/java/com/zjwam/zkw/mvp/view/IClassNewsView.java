package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ClassNewsBean;

import java.util.List;

public interface IClassNewsView {
    void setNews(List<ClassNewsBean.Item> newClass,List<ClassNewsBean.Item> teacher,List<ClassNewsBean.Item> fineClass,List<ClassNewsBean.Item> hotClass);
    void showMsg(String msg);
}
