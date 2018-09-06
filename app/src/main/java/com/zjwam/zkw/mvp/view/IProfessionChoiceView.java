package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ProfessionChoiceBean;

import java.util.List;

public interface IProfessionChoiceView {
    void setProfession(List<ProfessionChoiceBean> list);
    void showMsg(String msg);
}
