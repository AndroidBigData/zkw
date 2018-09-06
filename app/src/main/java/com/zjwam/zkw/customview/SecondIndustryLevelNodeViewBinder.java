package com.zjwam.zkw.customview;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.IndustrySelectBean;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by zxy on 17/4/23.
 */

public class SecondIndustryLevelNodeViewBinder extends CheckableNodeViewBinder {

    AppCompatCheckBox checkBox;
    CheckBoxSelector checkBoxSelector;
    public SecondIndustryLevelNodeViewBinder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_second_industry_level;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        checkBox.setText(((IndustrySelectBean)treeNode.getValue()).getName());
        checkBox.setTag(getAdapterPosition());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (getAdapterPosition() == (int)compoundButton.getTag()){
                    checkBoxSelector.selector(compoundButton.getText().toString(),b);
                }
            }
        });
    }
    public void setCheckBoxSelector(CheckBoxSelector checkBoxSelector){
        this.checkBoxSelector = checkBoxSelector;
    }
    public interface CheckBoxSelector{
        void selector(String selectorText,boolean isChecked);
    }
}
