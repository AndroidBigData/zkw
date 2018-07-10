package com.zjwam.zkw.util;

import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PageChangeListener {

    private RadioButton childButton;
    private RadioGroup rg;

    public PageChangeListener(RadioButton chaldButton, RadioGroup rg) {
        this.childButton = chaldButton;
        this.rg = rg;

    }

    public ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            childButton= (RadioButton) rg.getChildAt(position);
            childButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
