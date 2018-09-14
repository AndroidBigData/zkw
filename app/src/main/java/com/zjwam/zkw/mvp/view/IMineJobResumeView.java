package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.MineJobResumeBean;

import java.util.List;

public interface IMineJobResumeView {
    void setResume(List<MineJobResumeBean> list);
    void showMsg(String msg);
    void refresh();
    void refreshComplete();
    void deleteResume();
}
