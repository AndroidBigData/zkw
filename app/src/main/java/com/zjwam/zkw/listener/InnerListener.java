package com.zjwam.zkw.listener;


import com.zjwam.zkw.entity.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
