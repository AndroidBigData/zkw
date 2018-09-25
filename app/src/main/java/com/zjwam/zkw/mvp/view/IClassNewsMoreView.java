package com.zjwam.zkw.mvp.view;
import com.zjwam.zkw.entity.ClassInfo;

import java.util.List;

public interface IClassNewsMoreView {
    void getNews(List<ClassInfo> data, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
}
