package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.NewsBean;

import java.util.List;

public interface INewsMoreView {
    void getNews(List<NewsBean> data, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
}
