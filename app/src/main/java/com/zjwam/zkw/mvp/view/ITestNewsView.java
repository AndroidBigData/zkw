package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ClassTypeInfo;
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;

import java.util.List;

public interface ITestNewsView {
    void setNews(List<NewsBean> hyjj,List<NewsBean> ksdt,List<NewsBean> rdzt,List<NewsBean> jcdg,List<NewsBean> jqxd);
    void setInfo(List<ClassTypeInfo> newsInfo);
    void showMsg(String msg);
    void showDialog(List<TestQueryResultDialogBean> data);
    void refresh();
}
