package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.NewsBean;

import java.util.List;

public interface IMainNewsView {
    void setNews(List<NewsBean> news_n,List<NewsBean> news_w);
    void showMsg(String msg);
}
