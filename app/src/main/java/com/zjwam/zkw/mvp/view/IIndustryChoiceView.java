package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.IndustryChoiceBean;

import java.util.List;

public interface IIndustryChoiceView {
    void setIndustry(List<IndustryChoiceBean> list);
    void showMsg(String msg);
}
