package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;

import java.util.List;

public interface ISearchJobDetailsView {
    void setSearch(List<SearchJobDetailsPopBean.BasicBean> area,
                   List<SearchJobDetailsPopBean.BasicBean> education,
                   List<SearchJobDetailsPopBean.BasicBean> workyear,
                   List<SearchJobDetailsPopBean.BasicBean> money);
    void showMsg(String msg);
    void setSearchJob(List<JobHomeBean.Resume> list,int count);
    void refresh();
    void freshComplete();
}
