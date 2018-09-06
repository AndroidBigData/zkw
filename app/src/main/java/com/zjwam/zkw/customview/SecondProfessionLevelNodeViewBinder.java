package com.zjwam.zkw.customview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.R;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

public class SecondProfessionLevelNodeViewBinder extends BaseNodeViewBinder {

    TextView textView;
    ImageView imageView;
    public SecondProfessionLevelNodeViewBinder(View itemView) {
        super(itemView);
        textView =  itemView.findViewById(R.id.node_name_view);
        imageView =  itemView.findViewById(R.id.arrow_img);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_second_profession_level;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
        imageView.setRotation(treeNode.isExpanded() ? 90 : 0);
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (expand) {
            imageView.animate().rotation(90).setDuration(200).start();
        } else {
            imageView.animate().rotation(0).setDuration(200).start();
        }
    }
}
