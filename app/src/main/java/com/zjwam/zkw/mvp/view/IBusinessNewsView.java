package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.NewsBean;

import java.util.List;

public interface IBusinessNewsView {
    void setNews(List<NewsBean> zmfhq,List<NewsBean> zmft,List<NewsBean> yxxm,List<NewsBean> lxxm,List<NewsBean> ldxm);
    void showMsg(String msg);
}
