package com.zjwam.zkw.customview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.R;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by zxy on 17/4/23.
 */

public class FirstLevelNodeViewBinder extends BaseNodeViewBinder {
    TextView textView;
    public FirstLevelNodeViewBinder(View itemView) {
        super(itemView);
        textView =  itemView.findViewById(R.id.node_name_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_first_level;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
    }
}
