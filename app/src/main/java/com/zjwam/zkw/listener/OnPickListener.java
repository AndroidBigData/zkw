package com.zjwam.zkw.listener;


import com.zjwam.zkw.entity.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
}
