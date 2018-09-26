package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.NewsBean;

import java.util.List;

public interface IJobNewsView {
    void setNews(List<NewsBean> qzzx,List<NewsBean> zpzx);
    void showMsg(String msg);
}
