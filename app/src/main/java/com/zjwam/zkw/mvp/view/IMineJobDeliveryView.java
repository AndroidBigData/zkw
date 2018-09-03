package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.MineJobDeliveryBean;

import java.util.List;

public interface IMineJobDeliveryView {
    void addRecordItem(List<MineJobDeliveryBean.Resume> list, int count);
    void showMsg(String msg);
    void refresh();
    void refreshComplele();
}
