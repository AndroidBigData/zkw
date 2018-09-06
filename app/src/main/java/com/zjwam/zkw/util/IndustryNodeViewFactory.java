package com.zjwam.zkw.util;

import android.view.View;

import com.zjwam.zkw.customview.FirstLevelNodeViewBinder;
import com.zjwam.zkw.customview.SecondIndustryLevelNodeViewBinder;
import com.zjwam.zkw.customview.ThirdLevelNodeViewBinder;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class IndustryNodeViewFactory extends BaseNodeViewFactory {

    CheckBoxSelector checkBoxSelector;
    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view);
            case 1:
                SecondIndustryLevelNodeViewBinder secondIndustryLevelNodeViewBinder = new SecondIndustryLevelNodeViewBinder(view);
                secondIndustryLevelNodeViewBinder.setCheckBoxSelector(new SecondIndustryLevelNodeViewBinder.CheckBoxSelector() {
                    @Override
                    public void selector(String selectorText, boolean isChecked) {
                        checkBoxSelector.selector(selectorText,isChecked);
                    }
                });
                return secondIndustryLevelNodeViewBinder;
            default:
                return null;
        }
    }
    public void setCheckBoxSelector(CheckBoxSelector checkBoxSelector){
        this.checkBoxSelector = checkBoxSelector;
    }
    public interface CheckBoxSelector{
        void selector(String selectorText,boolean isChecked);
    }
}
